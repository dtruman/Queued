import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import edu.neumont.io.Bits;

public class main
{
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException
	{		
		passOffFile();
	}
	
	//Test to see if Huffman Works with AVL or Heap.....Note: To change go to HuffmanTree and change the priorityqueue type to whichever you would like to test
	static public void passOffFile() throws IOException, NoSuchAlgorithmException
	{
		byte[] b={0,1,2,2,1,0};
		HuffmanTree bt=new HuffmanTree(b);
		
		bt.inOrder();
		
		byte ab=1;
		byte ac=2;
		byte ad=0;
		
		Bits bits = new Bits();
		
		for ( int i = 0 ; i < b.length; i++ ) {
			bt.fromByte(b[i], bits);
		}
		while(bits.size()>0)
		{
			System.out.print(bt.toByte(bits));
		}
		System.out.println();
		
		HuffmanCompressor hc=new HuffmanCompressor();
		byte[] dec=hc.compress(bt,b);
		
		for(byte comp : dec)
		{
			System.out.print(comp);
		}
		System.out.println();
		for(byte decomp : hc.decompress(bt, 6, dec))
		{
			System.out.print(decomp);
		}
	}
}