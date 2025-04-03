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

    public static boolean isBookmarked(int vehicleId) {
        List<Bookmark> bookmarks = getAll();
        for (Bookmark bookmark : bookmarks) {
            if (bookmark.getVehicle().getId() == vehicleId) {
                return true;
            }
        }
        return false;
    }

    public static void bookmark(Vehicle vehicle) {
        ModelBank<Bookmark> bank = DatabaseHelper.getBookmarkBank();
        bank.add(new Bookmark(SessionService.getCurrentAdminId(), vehicle));
    }

    public static void removeBookmark(int vehicleId) {
        ModelBank<Bookmark> bank = DatabaseHelper.getBookmarkBank();
        for (Bookmark bookmark : getAll()) {
            if (bookmark.getVehicle().getId() == vehicleId) {
                bank.delete(bookmark.getId());
                break;
            }
        }
    }
}
