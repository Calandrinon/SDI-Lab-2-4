import Controller.RecordController;
import Controller.TransactionController;
import Controller.UserController;
import Model.Record;
import Model.RecordType;
import Model.Transaction;
import Model.User;
import Repository.Repository;
import UI.AdminUI;
import UI.ClientUI;
import UI.UI;
import Validator.RecordValidator;
import Validator.TransactionValidator;
import Validator.UserValidator;
import Repository.XmlFileRepository;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class deleteLater {
    public static void main(String[] args) {
        Record record1 = new Record(12, "yolo", 12, RecordType.CD);
        record1.setId(1);

        Record record2 = new Record(666, "666", 12, RecordType.VINYL);
        record2.setId(2);
        try {
            Repository<Integer, Record> recordRepository = new XmlFileRepository<Integer, Record>(new RecordValidator(), Record.recordEncoder, Record.recordDecoder, "src/Main/Repository/RepositoryFiles/records.xml");
            RecordController recordController = new RecordController(recordRepository);
            try {
                System.out.println(recordRepository.findOne(1));
                recordRepository.save(record1);
                System.out.println(recordRepository.findOne(1));

                if(recordRepository.findOne(1).get() == record1){
                    System.out.println("equal");
                }

                Scanner in = new Scanner(System.in);
                in.nextInt();
                recordRepository.update(record1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception ignore) {}
    }
}
