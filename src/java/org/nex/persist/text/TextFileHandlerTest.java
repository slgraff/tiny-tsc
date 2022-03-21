/*
 *  Copyright (C) 2004,2005  Jack Park,
 * 	mail : jackpark@gmail.com
 *
 * Apache 2 License
 */
package org.nex.persist.text;

/**
 * <p>Title: FrameStore</p>
 * <p>Description: Frame database</p>
 * <p>Copyright: Copyright (c) 2004, Jack Park</p>
 * <p>Company: NexistGroup</p>
 * @author Jack Park
 * @version 1.0
 */

public class TextFileHandlerTest {
  TextFileHandler handler = new TextFileHandler();
  String content = "Now is the time for all good men to come to the aid of their country.";
  String fileName = "bar.txt.gz";
  public TextFileHandlerTest() {

    handler.saveGZipFile(fileName,content);
    String result = handler.openGZipFile(fileName);
    System.out.println(result);
  }
  public static void main(String[] args) {
    TextFileHandlerTest textFileHandlerTest1 = new TextFileHandlerTest();
  }

}