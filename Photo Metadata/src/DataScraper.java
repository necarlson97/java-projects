import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DataScraper {
	
	String fileName;
	
	public static void main(String[] args) {
		new DataScraper("Leo Painting.jpg");
	}
	
	public DataScraper(String fileName) {
		this.fileName = fileName;
		
		try {
			scrape();
		} catch (IOException e) {
			System.out.println("Failed to scrape.");
			e.printStackTrace();
		}
	}
	
	void scrape() throws IOException {
		File file = new File(fileName);
		ImageInputStream iis = null;
		
		iis = ImageIO.createImageInputStream(file);
		Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
		
		while(readers.hasNext()) {
            ImageReader reader = readers.next();

            reader.setInput(iis, true);

            IIOMetadata metadata = reader.getImageMetadata(0);

            String[] names = metadata.getMetadataFormatNames();
            int length = names.length;
            for (int i = 0; i < length; i++) {
                System.out.println( "Format name: " + names[ i ] );
                displayMetadata(metadata.getAsTree(names[i]));
            }
		}
	}
	
	void displayMetadata(Node root) {
        displayMetadata(root, 0);
    }

    void indent(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("    ");
    }

    void displayMetadata(Node node, int level) {
        // print open tag of element
        indent(level);
        System.out.print("<" + node.getNodeName());
        NamedNodeMap map = node.getAttributes();
        if (map != null) {

            // print attribute values
            int length = map.getLength();
            for (int i = 0; i < length; i++) {
                Node attr = map.item(i);
                System.out.print(" " + attr.getNodeName() +
                                 "=\"" + attr.getNodeValue() + "\"");
            }
        }

        Node child = node.getFirstChild();
        if (child == null) {
            // no children, so close element and return
            System.out.println("/>");
            return;
        }

        // children, so close current tag
        System.out.println(">");
        while (child != null) {
            // print children recursively
            displayMetadata(child, level + 1);
            child = child.getNextSibling();
        }

        // print close tag of element
        indent(level);
        System.out.println("</" + node.getNodeName() + ">");
    }
}