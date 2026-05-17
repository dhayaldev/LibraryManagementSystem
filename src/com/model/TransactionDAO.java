package com.model;

import com.util.DBConnection;
import java.sql.*;

public class TransactionDAO {

    // Issue Book
    public static void issueBook(int bookId, String student) {
        try (Connection con = DBConnection.getConnection()) {
            // Reduce book quantity
            String updateQty = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ? AND quantity > 0";
            PreparedStatement ps = con.prepareStatement(updateQty);
            ps.setInt(1, bookId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Book not available!");
                return;
            }

            // Insert transaction
            String insertTxn = "INSERT INTO transactions (book_id, student_name, issue_date) VALUES (?, ?, CURDATE())";
            ps = con.prepareStatement(insertTxn);
            ps.setInt(1, bookId);
            ps.setString(2, student);
            ps.executeUpdate();

            System.out.println("Book issued successfully!");
        } catch (SQLException e) {
            System.err.println("Error issuing book: " + e.getMessage());
        }
    }

    // Return Book (Fine calculated in SQL)
    public static void returnBook(int transId) {
        try (Connection con = DBConnection.getConnection()) {
            // Update transaction with return_date & fine
            String updateTxn = "UPDATE transactions " +
                               "SET return_date = CURDATE(), " +
                               "fine = CASE " +
                               "           WHEN DATEDIFF(CURDATE(), issue_date) > 14 " +
                               "           THEN (DATEDIFF(CURDATE(), issue_date) - 14) * 5 " +
                               "           ELSE 0 " +
                               "       END " +
                               "WHERE trans_id = ? AND return_date IS NULL";
            PreparedStatement ps = con.prepareStatement(updateTxn);
            ps.setInt(1, transId);
            int updated = ps.executeUpdate();

            if (updated == 0) {
                System.out.println("Invalid transaction ID or already returned!");
                return;
            }

            // Increase book quantity back
            String getBook = "SELECT book_id, fine FROM transactions WHERE trans_id=?";
            ps = con.prepareStatement(getBook);
            ps.setInt(1, transId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                double fine = rs.getDouble("fine");

                String updateBook = "UPDATE books SET quantity = quantity + 1 WHERE book_id=?";
                PreparedStatement ps2 = con.prepareStatement(updateBook);
                ps2.setInt(1, bookId);
                ps2.executeUpdate();

                System.out.println("Book returned! Fine: ₹" + fine);
            }
        } catch (SQLException e) {
            System.err.println("Error returning book: " + e.getMessage());
        }
    }
}
