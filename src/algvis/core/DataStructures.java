/*******************************************************************************
 * Copyright (c) 2012 Jakub Kováč, Katarína Kotrlová, Pavol Lukča, Viktor Tomkovič, Tatiana Tóthová
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package algvis.core;

import java.lang.reflect.Constructor;

import algvis.aatree.AAPanel;
import algvis.avltree.AVLPanel;
import algvis.binomialheap.BinHeapPanel;
import algvis.bst.BSTPanel;
import algvis.btree.BPanel;
import algvis.btree.a234Panel;
import algvis.btree.a23Panel;
import algvis.daryheap.DaryHeapPanel;
import algvis.fibonacciheap.FibHeapPanel;
import algvis.heap.HeapPanel;
import algvis.intervaltree.IntervalPanel;
import algvis.lazybinomialheap.LazyBinHeapPanel;
import algvis.leftistheap.LeftHeapPanel;
import algvis.pairingheap.PairHeapPanel;
import algvis.redblacktree.RBPanel;
import algvis.rotations.RotPanel;
import algvis.scapegoattree.GBPanel;
import algvis.skewheap.SkewHeapPanel;
import algvis.skiplist.SkipListPanel;
import algvis.splaytree.SplayPanel;
import algvis.treap.TreapPanel;
import algvis.trie.TriePanel;
import algvis.unionfind.UnionFindPanel;

/**
 * The Class DataStructures. This class contains the list of all visualized data
 * structures. The menus with data structures are populated from this list. Each
 * data structure should have field dsName with its name and some superclass
 * should have field adtName with the name of the abstract data type (key to
 * resource bundle). The data structure can then be found in
 * "Data structures -> adtName -> dsName".
 */
public class DataStructures {
	@SuppressWarnings("rawtypes")
	static final Class[] PANEL = { BSTPanel.class, RotPanel.class,
			AVLPanel.class, a23Panel.class, a234Panel.class, BPanel.class,
			RBPanel.class, AAPanel.class, TreapPanel.class,
			SkipListPanel.class, GBPanel.class, SplayPanel.class,
			HeapPanel.class, DaryHeapPanel.class, LeftHeapPanel.class,
			SkewHeapPanel.class, BinHeapPanel.class, LazyBinHeapPanel.class,
			FibHeapPanel.class, UnionFindPanel.class, TriePanel.class, PairHeapPanel.class, 
			IntervalPanel.class};


	static final int N = PANEL.length;

	private static boolean check_range(int i) {
		if (i < 0 || i >= N) {
			System.out.println("DataStructures - index out of range.");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static Class<? extends DataStructure> DS(int i) {
		if (!check_range(i))
			return null;
		try {
			return (Class<? extends DataStructure>) (PANEL[i]
					.getDeclaredField("DS").get(null));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getName(int i) {
		if (!check_range(i))
			return "";
		String r = "";
		try {
			r = (String) (DS(i).getDeclaredField("dsName").get(null));
		} catch (Exception e) {
			System.out
					.println("DataStructures is unable to get field dsName - name of data structure: "
							+ i);
		}
		return r;
	}

	public static int getIndex(String s) {
		if (s == null)
			return -1;
		for (int i = 0; i < N; ++i) {
			if (s.equals(getName(i)))
				return i;
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public static String getADT(int i) {
		if (!check_range(i))
			return "";
		try {
			// find the superclass which has the adtName field set
			Class<? extends DataStructure> c = DS(i);
			while (true) {
				try {
					// DEBUG: System.out.println(c);
					c.getDeclaredField("adtName");
					break;
				} catch (NoSuchFieldException e) {
					c = (Class<? extends DataStructure>) c.getSuperclass();
				}
			}
			return (String) (c.getDeclaredField("adtName").get(null));
		} catch (Exception e) {
			System.out
					.println("DataStructures is unable to get field adtName - abstract data type of data structure: "
							+ i);
			return "";
		}
	}

	public static VisPanel getPanel(int i, Settings S) {
		switch (i) {
		case 0:
			return new BSTPanel(S);
		case 1:
			return new RotPanel(S);
		case 2:
			return new AVLPanel(S);
		case 3:
			return new a23Panel(S);
		case 4:
			return new a234Panel(S);
		case 5:
			return new BPanel(S);
		case 6:
			return new RBPanel(S);
		case 7:
			return new AAPanel(S);
		case 8:
			return new TreapPanel(S);
		case 9:
			return new SkipListPanel(S);
		case 10:
			return new GBPanel(S);
		case 11:
			return new SplayPanel(S);
		case 12:
			return new HeapPanel(S);
		case 13:
			return new DaryHeapPanel(S);
		case 14:
			return new LeftHeapPanel(S);
		case 15:
			return new SkewHeapPanel(S);
		case 16:
			return new BinHeapPanel(S);
		case 17:
			return new LazyBinHeapPanel(S);
		case 18:
			return new FibHeapPanel(S);
		case 19:
			return new UnionFindPanel(S);
		case 20:
			return new TriePanel(S);
		case 21:
			return new PairHeapPanel(S);
		case 22:
			return new IntervalPanel(S);
		}
		if (!check_range(i))
			return null;
		try {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			Constructor ct = DataStructures.PANEL[i]
					.getConstructor(Settings.class);
			return (VisPanel) ct.newInstance(S);
		} catch (Exception e) {
			System.out.println("DataStructures is unable to get panel: " + i);
			// System.out.println(((InvocationTargetException)e).getTargetException().toString());
			return null;
		}
	}
}
