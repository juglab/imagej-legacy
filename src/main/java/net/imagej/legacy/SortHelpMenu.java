package net.imagej.legacy;

import ij.IJ;
import ij.Menus;

import java.awt.*;
import java.awt.event.ActionListener;

public class SortHelpMenu {

	private static final String IJ1_WEB_LABEL = "ImageJ1";
	public static final String IJ2_WEB_LABEL = "ImageJ2";
	private static final String SEPARATOR = "-";

	private static final String[] IJ1_LABELS = new String[]{
			"ImageJ Website...", "ImageJ News...", "Documentation...", "Installation...", "Mailing List...", SEPARATOR,
			"Dev. Resources...", "Plugins...", "Macros...", "Macro Functions...", SEPARATOR,
			"Examples", SEPARATOR,
			"Update ImageJ..." };

	private static final String[] IJ2_LABELS = new String[]{
			"Switch to Modern Mode"};

	public static void sortWebLinks() {

		ActionListener ij1 = IJ.getInstance();
		if (ij1 != null) {
			final Menu helpMenu = Menus.getMenuBar().getHelpMenu();
			removeSeparators(helpMenu);
			helpMenu.insertSeparator(0);
			handleIJ2ResourceMenu(helpMenu);
			handleIJ1ResourceMenu(helpMenu);
		}

	}

	private static void handleIJ2ResourceMenu(Menu helpMenu) {
		Menu ij2WebMenu = (Menu)getMenuItem(helpMenu, IJ2_WEB_LABEL);
		if(ij2WebMenu != null) {
			helpMenu.remove(ij2WebMenu);
		}else {
			ij2WebMenu = new Menu(IJ2_WEB_LABEL);
		}
		helpMenu.insert(ij2WebMenu, 0);
		for (int i = 0; i < IJ2_LABELS.length; i++) {
			if(IJ2_LABELS[i].equals(SEPARATOR))
				ij2WebMenu.addSeparator();
			else
				moveItemToSubIfFound(helpMenu, ij2WebMenu, IJ2_LABELS[i]);
		}
	}

	private static void removeSeparators(Menu menu) {
		for(int i = menu.getItemCount()-1; i>= 0; i--) {
			if(menu.getItem(i).getLabel().equals(SEPARATOR)) menu.remove(i);
		}
	}

	private static void handleIJ1ResourceMenu(Menu helpMenu) {
		if(hasSubMenu(helpMenu, IJ1_WEB_LABEL)) return;
		final Menu sub = new Menu(IJ1_WEB_LABEL);
		helpMenu.insert(sub, 0);
		for (int i = 0; i < IJ1_LABELS.length; i++) {
			if(IJ1_LABELS[i].equals(SEPARATOR))
				sub.addSeparator();
			else
				moveItemToSubIfFound(helpMenu, sub, IJ1_LABELS[i]);
		}
	}

	private static void moveItemToSubIfFound(Menu menu, Menu sub, String itemLabel) {
		MenuItem item = getMenuItem(menu, itemLabel);
		if(item != null) {
			sub.add(item);
		}
	}

	private static boolean hasSubMenu(Menu menu, String subMenuLabel) {
		for(int i = 0; i < menu.getItemCount(); i++) {
			if(menu.getItem(i).getLabel().equals(subMenuLabel)) return true;
		}
		return false;
	}

	private static MenuItem getMenuItem(Menu menu, String itemLabel) {
		for(int i = 0; i < menu.getItemCount(); i++) {
			if(menu.getItem(i).getLabel().equals(itemLabel)) return menu.getItem(i);
		}
		return null;
	}
}
