package com.example.carshowcaseapp.services;

import com.example.carshowcaseapp.data.Bookmark;
import com.example.carshowcaseapp.data.Vehicle;
import com.example.carshowcaseapp.helpers.DatabaseHelper;
import com.example.carshowcaseapp.helpers.ModelBank;

import java.util.ArrayList;
import java.util.List;

public class BookmarkService {
    public static List<Bookmark> getAll() {
        ModelBank<Bookmark> bank = DatabaseHelper.getBookmarkBank();
        List<Bookmark> results = new ArrayList<>();
        for (Bookmark bookmark : bank.getAll()) {
            if (bookmark.getAdminId() == SessionService.getCurrentAdminId()) {
                results.add(bookmark);
            }
        }
        return results;
    }

    public static void bookmark(Vehicle vehicle) {
        ModelBank<Bookmark> bank = DatabaseHelper.getBookmarkBank();
        bank.add(new Bookmark(SessionService.getCurrentAdminId(), vehicle));
    }

    public static void delete(int id) {
        ModelBank<Bookmark> bank = DatabaseHelper.getBookmarkBank();
        bank.delete(id);
    }
}
