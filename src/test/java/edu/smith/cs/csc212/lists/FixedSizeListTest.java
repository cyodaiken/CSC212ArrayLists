package edu.smith.cs.csc212.lists;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.EmptyListError;
import me.jjfoley.adt.errors.RanOutOfSpaceError;

@SuppressWarnings("javadoc")
public class FixedSizeListTest {

	@Test
	public void testEmpty() {
		ListADT<String> data = new FixedSizeList<String>(0);
		Assert.assertEquals(0, data.size());
		Assert.assertEquals(true, data.isEmpty());
		data = new FixedSizeList<String>(32);
		Assert.assertEquals(0, data.size());
		Assert.assertEquals(true, data.isEmpty());
	}

	@Test(expected=EmptyListError.class)
	public void testRemoveFrontCrash() {
		ListADT<String> data = new FixedSizeList<String>(4);
		data.removeFront();
	}

	@Test(expected=EmptyListError.class)
	public void testRemoveBackCrash() {
		ListADT<String> data = new FixedSizeList<String>(4);
		data.removeBack();
	}

	@Test(expected=EmptyListError.class)
	public void testRemoveIndexCrash() {
		ListADT<String> data = new FixedSizeList<String>(4);
		data.removeIndex(3);
	}

	@Test
	public void testAddToFront() {
		ListADT<String> data = new FixedSizeList<String>(4);
		Assert.assertEquals(true, data.isEmpty());
		data.addFront("1");
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("1", data.getIndex(0));
		Assert.assertEquals(false, data.isEmpty());
		data.addFront("0");
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("0", data.getIndex(0));
		Assert.assertEquals("1", data.getIndex(1));
		Assert.assertEquals(false, data.isEmpty());
		data.addFront("-1");
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("-1", data.getIndex(0));
		Assert.assertEquals("0", data.getIndex(1));
		Assert.assertEquals("1", data.getIndex(2));
		Assert.assertEquals(false, data.isEmpty());
		data.addFront("-2");
		Assert.assertEquals("-2", data.getIndex(0));
		Assert.assertEquals("-1", data.getIndex(1));
		Assert.assertEquals("0", data.getIndex(2));
		Assert.assertEquals("1", data.getIndex(3));
		Assert.assertEquals(false, data.isEmpty());
	}

	@Test
	public void testAddToBack() {
		ListADT<String> data = new FixedSizeList<String>(4);
		data.addBack("1");
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("1", data.getIndex(0));
		data.addBack("0");
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("0", data.getIndex(1));
		Assert.assertEquals("1", data.getIndex(0));
		data.addBack("-1");
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("-1", data.getIndex(2));
		Assert.assertEquals("0", data.getIndex(1));
		Assert.assertEquals("1", data.getIndex(0));
		data.addBack("-2");
		Assert.assertEquals("-2", data.getIndex(3));
		Assert.assertEquals("-1", data.getIndex(2));
		Assert.assertEquals("0", data.getIndex(1));
		Assert.assertEquals("1", data.getIndex(0));
	}

	/**
	 * Helper method to make a full list.
	 * @return [a,b,c,d]
	 */
	public ListADT<String> makeFullList() {
		ListADT<String> data = new FixedSizeList<String>(4);
		data.addBack("a");
		data.addBack("b");
		data.addBack("c");
		data.addBack("d");
		return data;
	}

	@Test(expected=RanOutOfSpaceError.class)
	public void testAddBackFull() {
		makeFullList().addBack("no space");
	}

	@Test(expected=RanOutOfSpaceError.class)
	public void testAddFrontFull() {
		makeFullList().addFront("no space");
	}

	@Test(expected=RanOutOfSpaceError.class)
	public void testAddIndexFull() {
		makeFullList().addIndex(2, "no space");
	}

	@Test
	public void testRemoveFront() {
		ListADT<String> data = makeFullList();
		Assert.assertEquals(4, data.size());
		Assert.assertEquals("a", data.removeFront());
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("b", data.removeFront());
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("c", data.removeFront());
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("d", data.removeFront());
		Assert.assertEquals(0, data.size());
	}

	@Test
	public void testRemoveBack() {
		ListADT<String> data = makeFullList();
		Assert.assertEquals(4, data.size());
		Assert.assertEquals("d", data.removeBack());
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("c", data.removeBack());
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("b", data.removeBack());
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("a", data.removeBack());
		Assert.assertEquals(0, data.size());
	}

	@Test
	public void testRemoveIndex() {
		ListADT<String> data = makeFullList();
		Assert.assertEquals(4, data.size());
		Assert.assertEquals("c", data.removeIndex(2));
		Assert.assertEquals(3, data.size());
		Assert.assertEquals("d", data.removeIndex(2));
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("b", data.removeIndex(1));
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("a", data.removeIndex(0));
		Assert.assertEquals(0, data.size());
	}

	@Test
	public void testAddIndexFront() {
		System.out.println("INDEX FRONT");
		ListADT<String> data = new FixedSizeList<>(10);
		data.addBack("A");
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("A", data.getFront());
		data.addIndex(0, "B");
		System.out.println("DATA: " + data);
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("B", data.getFront());
		System.out.println("GETBACK: " + data.getBack());
		Assert.assertEquals("A", data.getBack());
		System.out.println("-------------------");
	}

	@Test
	public void testAddIndexBack() {
		System.out.println("INDEX BACK");
		ListADT<String> data = new FixedSizeList<>(10);
		data.addBack("A");
		Assert.assertEquals(1, data.size());
		Assert.assertEquals("A", data.getFront());
		data.addIndex(1, "B");
		Assert.assertEquals(2, data.size());
		Assert.assertEquals("A", data.getFront());
		Assert.assertEquals("B", data.getBack());
	}

	@Test
	public void testAddIndexCenter() {
		ListADT<String> data = new FixedSizeList<>(10);
		data.addBack("A");
		data.addBack("C");
		data.addBack("D");
		data.addBack("E");
		Assert.assertEquals(4, data.size());

		data.addIndex(1, "B");
		Assert.assertEquals(5, data.size());
		Assert.assertEquals("B", data.getIndex(1));
	}

	@Test
	public void testGetFront() {
		ListADT<String> data = makeFullList();
		assertEquals("a", data.getFront());
	}

	@Test
	public void testGetBack() {
		ListADT<String> data = makeFullList();
		assertEquals("d", data.getBack());
	}

	@Test(expected=EmptyListError.class)
	public void testGetFrontCrash() {
		ListADT<String> data = new FixedSizeList<>(3);
		data.getFront();
	}

	@Test(expected=EmptyListError.class)
	public void testGetBackCrash() {
		ListADT<String> data = new FixedSizeList<>(3);
		data.getBack();
	}

	@Test(expected=BadIndexError.class)
	public void testGetIndexLow() {
		ListADT<String> data = makeFullList();
		data.getIndex(-2);
	}

	@Test(expected=BadIndexError.class)
	public void testGetIndexHigh() {
		ListADT<String> data = makeFullList();
		data.getIndex(data.size());
	}

	@Test(expected=BadIndexError.class)
	public void testGetIndexHighEasy() {
		ListADT<String> data = makeFullList();
		data.getIndex(data.size()*2);
	}

}
