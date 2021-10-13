package filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

import structures.Queue;

/**
 * An iterator to perform a level order traversal of part of a 
 * filesystem. A level-order traversal is equivalent to a breadth-
 * first search.
 * 
 * @author liberato
 *
 */
public class LevelOrderIterator extends FileIterator<File> {	
	/**
	 * Instantiate a new LevelOrderIterator, rooted at the rootNode.
	 * @param rootNode
	 * @throws FileNotFoundException if the rootNode does not exist
	 */
	
	private Queue<File> fileQueue = new Queue<File>();
	private Queue<File> setUpQueue = new Queue<File>();
	private File rootNode;
	//private File currNode;
	
	public LevelOrderIterator(File rootNode) throws FileNotFoundException {
		if(!rootNode.exists()) throw new FileNotFoundException("root does not exist");
		this.rootNode = rootNode;
		
		fileQueue.enqueue(rootNode);
		setUpQueue.enqueue(rootNode);
		
		while(!setUpQueue.isEmpty()){
			if(setUpQueue.peek().isDirectory()){
				for(File f : setUpQueue.peek().listFiles()){
					setUpQueue.enqueue(f);
					fileQueue.enqueue(f);
				}
			}
			setUpQueue.dequeue();
		}
		
	}
	
	@Override
	public boolean hasNext() {
		return !fileQueue.isEmpty();
	}

	@Override
	public File next() throws NoSuchElementException {
		if(!hasNext()) throw new NoSuchElementException("tried to get next from empty queue");
		return fileQueue.dequeue();
	}

	@Override
	public void remove() {
		// Leave this one alone.
		throw new UnsupportedOperationException();		
	}

}
