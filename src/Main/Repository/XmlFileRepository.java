package Repository;

import Exceptions.ValidationException;
import Model.BaseEntity;
import Validator.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.print.Doc;
import javax.swing.text.html.Option;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class XmlFileRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    private final Validator<T> validator;
    private final BiFunction<T, Document, Node> objectEncoder;
    private final Function<Element, T> objectDecoder;
    private String filename;

    /**
     *
     * @param validator a validator object corresponding to the object that is being stored
     * @param objectEncoder a BiFunction which encodes a given T object into a Node
     * @param objectDecoder a Function which decodes an Element and returns a corresponding T object
     * @param fileName the name of the file where the objects will be stored
     */

    public XmlFileRepository(Validator<T> validator, BiFunction<T, Document, Node> objectEncoder, Function<Element, T> objectDecoder, String fileName) {
        this.validator = validator;
        this.objectEncoder = objectEncoder;
        this.objectDecoder = objectDecoder;
        this.filename = fileName;
    }

    /**
     *
     * @param element the given Element
     * @param document the document where nodes corresponding to the given element's attributes
     */

    private void addElementToDOM(T element, Document document){
        Element root = document.getDocumentElement();
        Node elementNode = this.objectEncoder.apply(element, document);
        root.appendChild(elementNode);
    }

    /**
     *
     * @param id the unique identifier of the object you want to add
     *            must be not null.
     * @return an Optional which wraps the object if it was found or an empty one otherwise
     */

    @Override
    public Optional<T> findOne(ID id) {
        Optional.ofNullable(id).orElseThrow(() -> new RuntimeException("the id cannot be null"));

        List<T> list = new ArrayList<>();

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document root = db.parse(this.filename);
            Element listOfElements = root.getDocumentElement();
            NodeList elementList = listOfElements.getChildNodes();
            for (int i = 0; i < elementList.getLength(); ++i) {
                if (!(elementList.item(i) instanceof Element)) {
                    continue;
                }
                T object = objectDecoder.apply((Element) elementList.item(i));
                list.add(object);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return list.stream().filter(e -> e.getId() == id).findFirst();
    }

    /**
     *
     * @return the list of all elements stored inside the xml file
     *
     */

    @Override
    public Iterable<T> findAll() {
        List<T> list = new ArrayList<>();
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document root = db.parse(this.filename);
            Element listOfElements = root.getDocumentElement();
            NodeList elementList = listOfElements.getChildNodes();
            for (int i = 0; i < elementList.getLength(); ++i) {
                if (!(elementList.item(i) instanceof Element))
                    continue;
                T object = objectDecoder.apply((Element) elementList.item(i));
                list.add(object);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param entity the entity you want to add
     *            must not be null.
     * @return an empty optional if the element was successfully added or the entity if it was already there
     * @throws ValidationException
     *            if the entity is not valid
     */

    @Override
    public Optional<T> save(T entity) throws ValidationException {
        Optional.ofNullable(entity).orElseThrow(() -> new RuntimeException("the id cannot be null"));
        validator.validate(entity);

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(this.filename);

            NodeList elementlist = document.getDocumentElement().getChildNodes();
            for(int i = 0 ; i < elementlist.getLength() ; ++i){
                if (!(elementlist.item(i) instanceof Element)) {
                    continue;
                }
                if(((Element)elementlist.item(i)).getAttribute("id").equals(entity.getId().toString())){
                    return Optional.of(entity);
                }
            }

            addElementToDOM(entity, document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(this.filename))
            );
        }catch (Exception ignore){}
        return Optional.empty();
    }

    /**
     *
     * @param id the id of the element you want to remove
     *            must not be null.
     * @return an empty Optional if the element was successfully removed
     */

    @Override
    public Optional<T> delete(ID id) {
        Optional<Node> returned = Optional.empty();
        Optional.ofNullable(id).orElseThrow(() -> new RuntimeException("the id cannot be null"));
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(this.filename);

            returned = getNodeByID(
                    document.getDocumentElement()
                            .getChildNodes()
                    , id);

            if(returned.isEmpty()) {return Optional.empty();}

            document.getDocumentElement().removeChild(returned.get());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(this.filename))
            );
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Optional.of(this.objectDecoder.apply((Element) returned.get()));
    }

    /**
     *
     * @param nodeList the node list where the element will be searched
     * @param id the id of the element we are looking for
     * @return an Optional which wraps the element or an empty Optional if it doesn't exist
     */

    private Optional<Node> getNodeByID(NodeList nodeList, ID id){
        Node node = null;
        for(int i = 0 ; i < nodeList.getLength() ; i++){
            if (!(nodeList.item(i) instanceof Element)) {
                continue;
            }
            if(this.objectDecoder.apply((Element) nodeList.item(i)).getId() == id){
                return Optional.ofNullable(nodeList.item(i));
            }
        }
        return Optional.empty();
    }

    /**
     *
     * @param entity the entity we want updated
     *            must not be null.
     * @return an empty Optional if the element was successfully updated
     * @throws ValidationException
     *           the given entity is not valid
     */

    @Override
    public Optional<T> update(T entity) throws ValidationException {
        Optional.ofNullable(entity).orElseThrow(() -> new ValidationException("the entity cannot be null"));
        validator.validate(entity);
        Optional<Node> returned = Optional.empty();
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(this.filename);

            returned = getNodeByID(document.getDocumentElement().getChildNodes(), entity.getId());

            if(returned.isEmpty()){return Optional.empty();}

            document.getDocumentElement().replaceChild(
                    this.objectEncoder.apply(entity, document),
                    returned.get()
            );

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(
                    new DOMSource(document),
                    new StreamResult(new File(this.filename))
            );
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Optional.of(this.objectDecoder.apply((Element) returned.get()));
    }
}
