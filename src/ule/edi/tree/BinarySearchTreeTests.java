package ule.edi.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeTests {

	/*
	 * 10 | 5 | | 2 | | | ∅ | | | ∅ | | ∅ | 20 | | 15 | | | ∅ | | | ∅ | | 30 | | | ∅
	 * | | | ∅
	 */
	private BinarySearchTreeImpl<Integer> ejemplo = null;

	/*
	 * 10 | 5 | | 2 | | | ∅ | | | ∅ | | ∅ | 20 | | 15 | | | 12 | | | | ∅ | | | | ∅ |
	 * | ∅
	 */
	private BinarySearchTreeImpl<Integer> other = null;

	@Before
	public void setupBSTs() {

		ejemplo = new BinarySearchTreeImpl<Integer>();
		ejemplo.insert(10, 20, 5, 2, 15, 30);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");

		other = new BinarySearchTreeImpl<Integer>();
		other.insert(10, 20, 5, 2, 15, 12);

	}

	@Test
	public void testInsertList() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		List<Integer> lista = new ArrayList<>();
		lista.add(10);
		lista.add(20);
		lista.add(5);
		lista.add(2);
		lista.add(15);
		lista.add(30);
		assertEquals("[10, 20, 5, 2, 15, 30]", lista.toString());
		assertEquals(6, arbol.insert(lista));
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}", arbol.toString());

	}

	@Test
	public void testRemoveCountMayor1() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}");
		ejemplo.remove(20);
		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}");
	}

	@Test
	public void testRemoveCountMayor1HastaVaciar() {
		ejemplo.insert(20);
		ejemplo.insert(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(3), {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20(2), {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
		ejemplo.remove(20);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {30, {15, ∅, ∅}, ∅}}", ejemplo.toString());
	}

	@Test
	public void testRemoveHoja() {
		ejemplo.remove(30);
		Assert.assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, ∅}}", ejemplo.toString());
	}

	@Test
	public void testRemove1Hijo() {
		ejemplo.remove(5);
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}", ejemplo.toString());
	}

	@Test
	public void testRemove2Hijos() {
		ejemplo.remove(10);
		Assert.assertEquals("{15, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}", ejemplo.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertException() {
		Integer i = null;
		other.insert(i);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContainsNull() {
		other.contains(null);
	}

	@Test
	public void testContains() {
		assertFalse(other.isEmpty());
		assertTrue(other.contains(10));
		assertTrue(other.contains(12));
		assertFalse(other.contains(100));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveNullElement() {
		Integer i = null;
		other.remove(i);
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveNoSuchElement() {
		other.remove(11);
	}

	@Test
	public void testSize() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		assertEquals(6, other.size());
		assertEquals(0, arbol.size());
		assertTrue(arbol.insert(1));
		assertEquals(1, arbol.size());
		assertFalse(arbol.insert(1));
		assertEquals(1, arbol.size());
		assertTrue(arbol.insert(3));
		assertEquals(2, arbol.size());

	}

	@Test
	public void testInstancesCount() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		BinarySearchTreeImpl<Integer> arbol1 = new BinarySearchTreeImpl<>();

		assertEquals(6, other.instancesCount());
		assertEquals(0, arbol.instancesCount());

		assertTrue(arbol.insert(1));
		assertTrue(arbol.insert(3));
		assertTrue(arbol.insert(12));
		assertTrue(arbol.insert(5));
		assertEquals(4, arbol.instancesCount());
		arbol1.insert(50, 30, 30, 10, 40, 40, 40, 40, 80, 80, 60);
		assertEquals("{50, {30(2), {10, ∅, ∅}, {40(4), ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}", arbol1.toString());
		assertEquals(11, arbol1.instancesCount());

	}

	@Test
	public void testIteratorWidth() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		// {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
		// 50, 30, 80, 10,40, 60
		arbol.insert(50, 30, 30, 10, 40, 80, 80, 60);
		assertEquals("{50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}", arbol.toString());

		Iterator<Integer> iter = arbol.iteratorWidth();

		assertTrue(iter.hasNext());
		assertEquals("50", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("30", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("80", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("10", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("40", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("60", iter.next().toString());

		assertFalse(iter.hasNext());
	}

	@Test
	public void testIteratorWidthInstances() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		// {50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}
		// 50, 30, 80, 10,40, 60
		// 50, 30, 30, 80,80, 10, 40, 60
		arbol.insert(50, 30, 30, 10, 40, 80, 80, 60);
		assertEquals("{50, {30(2), {10, ∅, ∅}, {40, ∅, ∅}}, {80(2), {60, ∅, ∅}, ∅}}", arbol.toString());

		Iterator<Integer> iter = arbol.iteratorWidthInstances();

		assertTrue(iter.hasNext());
		assertEquals("50", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("30", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("30", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("80", iter.next().toString());
		assertTrue(iter.hasNext());
		assertEquals("80", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("10", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("40", iter.next().toString());

		assertTrue(iter.hasNext());
		assertEquals("60", iter.next().toString());

		assertFalse(iter.hasNext());

	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveAllTreeEmpty() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		arbol.removeAll(1);
	}
	@Test(expected = NoSuchElementException.class)
	public void testRemoveNoElementInTree() {
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		other.removeAll(3);
	}
	
	

	@Test
	public void testRemoves() {
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");
		assertEquals(1, other.remove(5, 6, 1));
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}", other.toString());
		other.insert(12, 12);
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, {12(3), ∅, ∅}, ∅}, ∅}}", other.toString());
		other.remove(12, 2);
		Assert.assertEquals("{10, {2, ∅, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}", other.toString());
		other.insert(12, 12, 16, 16, 17, 17);
		assertEquals("{10, {2, ∅, ∅}, {20, {15, {12(3), ∅, ∅}, {16(2), ∅, {17(2), ∅, ∅}}}, ∅}}", other.toString());
		other.removeAll(16);
		assertEquals("{10, {2, ∅, ∅}, {20, {15, {12(3), ∅, ∅}, {17(2), ∅, ∅}}, ∅}}", other.toString());
		other.removeAll(12);
		assertEquals("{10, {2, ∅, ∅}, {20, {15, ∅, {17(2), ∅, ∅}}, ∅}}", other.toString());

		Assert.assertEquals(ejemplo.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}");
		assertEquals(2, ejemplo.remove(5, 30, 1));
		assertEquals("{10, {2, ∅, ∅}, {20, {15, ∅, ∅}, ∅}}", ejemplo.toString());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetContentWithPathException() {
		other.getContentWithPath("0 1");
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetContentWithPathException2Left() {
		other.getContentWithPath("1000");
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetContentWithPathException2Right() {
		other.getContentWithPath("11");
	}

	@Test(expected = NoSuchElementException.class)
	public void testGetSubtreeWIthPathException() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		arbol.getSubtreeWithPath("");

	}

	@Test
	public void testGetContentWithPathAndSubtree() {
		Assert.assertEquals(other.toString(), "{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}");

		assertEquals(10, other.getContentWithPath(""), 0);
		assertEquals(20, other.getContentWithPath("1"), 0);
		assertEquals(5, other.getContentWithPath("0"), 0);
		assertEquals(12, other.getContentWithPath("100"), 0);

		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, {15, {12, ∅, ∅}, ∅}, ∅}}", other.getSubtreeWithPath("").toString());
		assertEquals("{20, {15, {12, ∅, ∅}, ∅}, ∅}", other.getSubtreeWithPath("1").toString());
	}

	@Test
	public void testtagRightChildrenInorder() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		assertEquals(0, arbol.tagRightChildrenInorder());
		arbol.insert(10, 5, 2, 20, 30);
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}", arbol.toString());
		assertEquals(2, arbol.tagRightChildrenInorder());
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20 [(inorder, 4)], ∅, {30 [(inorder, 5)], ∅, ∅}}}", arbol.toString());

		arbol.insert(40, 50);
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20 [(inorder, 4)], ∅, {30 [(inorder, 5)], ∅, {40, ∅, {50, ∅, ∅}}}}}", arbol.toString());
		assertEquals(4, arbol.tagRightChildrenInorder());
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20 [(inorder, 4)], ∅, {30 [(inorder, 5)], ∅, {40 [(inorder, 6)], ∅, {50 [(inorder, 7)], ∅, ∅}}}}}", arbol.toString());

	}

	@Test
	public void testtagDescendent() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		arbol.insert(10, 5, 2, 20, 30);
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}", arbol.toString());
		arbol.tagDescendent();
		assertEquals(
				"{10 [(descend, 3)], {5 [(descend, 4)], {2 [(descend, 5)], ∅, ∅}, ∅}, {20 [(descend, 2)], ∅, {30 [(descend, 1)], ∅, ∅}}}",
				arbol.toString());
		arbol.insert(40, 50);
		arbol.tagDescendent();
		assertEquals(
				"{10 [(descend, 5)], {5 [(descend, 6)], {2 [(descend, 7)], ∅, ∅}, ∅}, {20 [(descend, 4)], ∅, {30 [(descend, 3)], ∅, {40 [(descend, 2)], ∅, {50 [(descend, 1)], ∅, ∅}}}}}",
				arbol.toString());

	}

	@Test
	public void testparentChildPairsTagPostorder() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		List<String> lista = new LinkedList<>();
		arbol.insert(10, 5, 2, 20, 30);
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}", arbol.toString());
		arbol.parentChildPairsTagPostorder(lista);

		assertEquals("[(5,2), (20,30), (10,5), (10,20)]", lista.toString());
		assertEquals(
				"{10 [(postorder, 5)], {5 [(postorder, 2)], {2 [(postorder, 1)], ∅, ∅}, ∅}, {20 [(postorder, 4)], ∅, {30 [(postorder, 3)], ∅, ∅}}}",
				arbol.toString());

	}
	
	@Test
	public void testBrotherHasSameStruct() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		assertFalse(arbol.HasBrotherSameStruc());
		arbol.insert(10);
		assertFalse(arbol.getLeftBST().HasBrotherSameStruc());		
		arbol.insert(5, 2, 20, 30);
		assertEquals("{10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}", arbol.toString());
		assertFalse(arbol.getLeftBST().HasBrotherSameStruc());
		arbol.insert(7, 15);
		assertEquals("{10, {5, {2, ∅, ∅}, {7, ∅, ∅}}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}", arbol.toString());
		assertTrue(arbol.getLeftBST().HasBrotherSameStruc());
		assertTrue(arbol.getRightBST().HasBrotherSameStruc());
		assertTrue(arbol.getRightBST().getLeftBST().HasBrotherSameStruc());
	}
	

	@Test
	public void testBrotherHasSameStruct2() {
		BinarySearchTreeImpl<Integer> arbol = new BinarySearchTreeImpl<>();
		arbol.insert(10, 5, 20);
		assertTrue(arbol.getLeftBST().HasBrotherSameStruc());
		assertTrue(arbol.getRightBST().HasBrotherSameStruc());
		arbol.insert(2);
		assertFalse(arbol.getLeftBST().HasBrotherSameStruc());
		assertFalse(arbol.getRightBST().HasBrotherSameStruc());
		
		assertFalse(arbol.getLeftBST().getLeftBST().HasBrotherSameStruc());
		arbol.insert(7, 15, 30);
		assertEquals("{10, {5, {2, ∅, ∅}, {7, ∅, ∅}}, {20, {15, ∅, ∅}, {30, ∅, ∅}}}", arbol.toString());
		
		arbol.insert(32);
		assertFalse(arbol.getRightBST().getRightBST().HasBrotherSameStruc());
		assertFalse(arbol.getLeftBST().getRightBST().getRightBST().HasBrotherSameStruc());
		
		
		
	}
		

}
