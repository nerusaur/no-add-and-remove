

package library;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.table.TableRowSorter;

public class Dashboard extends javax.swing.JFrame {

    private List<Object[]> rowData = new ArrayList<>();
    public ArrayList<Book> books = listOfBooks(); // Initialize books with the list of books
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    private ArrayList<String[]> borrowRecords = new ArrayList<>();
    ArrayList<Book> notAvailableBooks = getAllNotAvailableBooks();
    private ArrayList<BorrowedBook> borrowedBooks1 = new ArrayList<>();
    private ArrayList<BorrowedBook> hiram = TheLastOne();
    ArrayList<Book> availableBooks = getAllAvailableBooks();
   private static Dashboard instance;
    private JTextField jtBorrowName;
    private int selectedRowIndex = -1;
    private StaffForm staffForm;
    private JTextField jtReturnTitle;
    private DefaultTableModel modeljTable2;
    //for
    String borrowerName;
    String borrowDateString;
    String returnDateString;
    String newBook;
    
    public Dashboard() {
        getAllAvailableBooks();
        staffForm = new StaffForm(this);
        initComponents();
        addRowToTable();
        jTableAvailable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    selectedRowIndex = jTableAvailable.getSelectedRow();
                }
            }
            
        });
        
    }
  
   public Dashboard(StaffForm staffForm) {
    this.staffForm = staffForm;
    initComponents();
}


    public ArrayList<Book> listOfBooks(){
    ArrayList<Book> list = new ArrayList<>();
    list.add(new Book("Computer Programming 3", "Neil Sindayen", "Programming", true));
    list.add(new Book("Discrete Structure 2", "Neil Sindayen", "Programming", true));
    list.add(new Book("Discrete Structure 1", "Vladlen Koltun", "Programming", true));
    list.add(new Book("Electrical Circuits 1", "Neil Sindayen", "Engineering", true));
    list.add(new Book("Electrical Circuits 2", "Neil Sindayen", "Engineering", true));
    list.add(new Book("Environmental Engineering", "Neil Sindayen", "Engineering", true));
    list.add(new Book("Economics", "Neil Sindayen", "Engineering", true));
    list.add(new Book("Financial Management", "Neil Sindayen", "Business & Accounting", true));
    list.add(new Book("Investment & Portfolio Management", "Neil Sindayen", "Business & Accounting", true));
    list.add(new Book("Taxation", "Neil Sindayen", "Business & Accounting", true));
    list.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", "Programming", true));
    list.add(new Book("Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin", "Programming", true));
    list.add(new Book("The Pragmatic Programmer: Your Journey to Mastery", "Andrew Hunt", "Programming", true));
    list.add(new Book("Financial Accounting", "Jerry J. Weygandt", "Business & Accounting", true));
    list.add(new Book("Management Accounting", "Anthony A. Atkinson", "Business & Accounting", true));
    list.add(new Book("Auditing and Assurance Services", "Alvin A. Arens", "Business & Accounting", true));
    list.add(new Book("Mechanics of Materials", "Russell C. Hibbeler", "Engineering", true));
    list.add(new Book("Fluid Mechanics", "Frank M. White", "Engineering", true));
    list.add(new Book("Engineering Thermodynamics", "Michael J. Moran", "Engineering", true));
    Collections.sort(list, Comparator.comparing(Book::getTitle));
    return list;
}

public ArrayList<Book> getAllAvailableBooks() {
    ArrayList<Book> availableBooks = new ArrayList<>();
    for (Book book : books) {
        if (book.isAvailable()) {
            availableBooks.add(book);
        }
    }
    return availableBooks;
}
public ArrayList<Book> getAllNotAvailableBooks() {
    ArrayList<Book> notAvailableBooks = new ArrayList<>();
    for (Book book : books) {
        if (!book.isAvailable()) {
            notAvailableBooks.add(book);
            System.out.println(getAllNotAvailableBooks());
        }
        
        return notAvailableBooks;
    }

    // Add the name, date borrowed, and date to return from text fields to the list
    for (Book book : notAvailableBooks) {
        book.setName(jtBorrowName.getText());
        book.setBorrowDate(jtBorrowDate.getText());
        book.setReturnDate(jtReturnDate.getText());
    }

    return notAvailableBooks;
}

private void addRowToTable() {
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.setRowCount(0); // Clear the table

    for (Book book : availableBooks) {
        if (book.isAvailable()) { // Only add available books
            Object[] row = {book.getTitle(), book.getAuthor(), book.getCategory()};
            model.addRow(row);
        }
    }
}

private boolean isBookBorrowed(Book book) {
    for (Book notAvailableBook : notAvailableBooks) {
        if (notAvailableBook.getTitle().equals(book.getTitle()) && notAvailableBook.getAuthor().equals(book.getAuthor())) {
            System.out.println("Book is borrowed: " + notAvailableBook.getTitle() + " by " + notAvailableBook.getAuthor());
            return true;
        }
    }
    
    return false;
}

 public ArrayList<Book> searchBooksByTitle(String title) {
    ArrayList<Book> list = listOfBooks();
    ArrayList<Book> filteredList = new ArrayList<>();
    for (Book book : list) {
        if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
            filteredList.add(book);
        }
    }
    return filteredList;
}
  public ArrayList<Book> searchBooks(String searchTitle, String searchAuthor) {
    ArrayList<Book> list = listOfBooks();
    ArrayList<Book> filteredList = new ArrayList<>();
         for (Book book : list) {
            String title = book.getTitle().toLowerCase();
            String author = book.getAuthor().toLowerCase();
            if (title.contains(searchTitle.toLowerCase()) && author.contains(searchAuthor.toLowerCase())) {
                filteredList.add(book);
            }
        }
        return filteredList;
    }

public void addRowToTable(ArrayList<Book> filteredList) {
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.setRowCount(0); // Clear existing rows
    Object rowData[] = new Object[2];
    for (int i = 0; i < filteredList.size(); i++) {
        rowData[0] = filteredList.get(i).getTitle();
        rowData[1] = filteredList.get(i).getAuthor();
        model.addRow(rowData);
        
        // Highlight the row if it matches the search term (title or author)
        String searchTitle = jtSearchTittle.getText().toLowerCase();
        String searchAuthor = jtSearchAuthor.getText().toLowerCase();
        String currentTitle = filteredList.get(i).getTitle().toLowerCase();
        String currentAuthor = filteredList.get(i).getAuthor().toLowerCase();
        if (currentTitle.contains(searchTitle) || currentAuthor.contains(searchAuthor)) {
            jTableAvailable.setRowSelectionInterval(i, i);
        }
        
    }  
}

 private void checkBorrowedBooksButtonActionPerformed(java.awt.event.ActionEvent evt) {
        TheLastOne();
    }
public ArrayList<BorrowedBook> TheLastOne(){
    ArrayList<BorrowedBook> theLastOne = new ArrayList<>();
    for (BorrowedBook borrowedBook : borrowedBooks1) {
        theLastOne.add(borrowedBook);
    }
    //staffForm.updateTable(theLastOne);
    for (BorrowedBook borrowedBook : theLastOne) {
        System.out.println("Title: " + borrowedBook.getTitle());
        System.out.println("Author: " + borrowedBook.getAuthor());
        System.out.println("Borrower Name: " + borrowedBook.getBorrowerName());
        System.out.println("Borrow Date: " + borrowedBook.getBorrowDate());
        System.out.println("Return Date: " + borrowedBook.getReturnDate());
        System.out.println("--------------------------");
    
}
        return theLastOne;
}


private void updateTableWithAvailableBooks() {
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.setRowCount(0); // Clear previous table data

    /*for (Book book : listOfBooks()) {
        if (book.isAvailable()) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getCategory()});
        }
    }*/
}
public void addBorrowedBook(String title, String author, String borrowerName, String borrowDate, String returnDate) {
    BorrowedBook borrowedBook = new BorrowedBook(title, author, borrowerName, borrowDate, returnDate);
    borrowedBooks1.add(borrowedBook);
}

public void addBookToAvailableBooks(Book book) {
    availableBooks.add(book);
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.addRow(new Object[]{book.getTitle(), book.getAuthor()});
    System.out.println("addBookToAvailableBooks" + book );
}

public void addBookToDashboard(String title, String author, String category) {
    // Create a new Book instance
    Book newBook = new Book(title, author, category);

    // Add the new book to the availableBooks ArrayList
    availableBooks.add(newBook);
    updateTableWithAvailableBooks();

    // Update jTableAvailable in the Dashboard
    DefaultTableModel modelTableAvailable = (DefaultTableModel) jTableAvailable.getModel();
    modelTableAvailable.addRow(new Object[]{title, author});

    // Print the updated list of books
    System.out.println("Available Books:DASHBOARD");
    for (Book book : availableBooks) {
        System.out.println(book.getTitle() + " by " + book.getAuthor());
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbLogin = new javax.swing.JButton();
        jButtonExit = new javax.swing.JButton();
        jpSearch = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtSearchTittle = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtSearchAuthor = new javax.swing.JTextField();
        jbSearch = new javax.swing.JButton();
        jpBorrow = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jtBorrowerName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtBorrowDate = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtReturnDate = new javax.swing.JTextField();
        jbBorrow = new javax.swing.JButton();
        jpBorrow1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jbReturnBook = new javax.swing.JButton();
        jtReturnName1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAvailable = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableBorrowed = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jComboBoxIndex = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);

        jPanel1.setBackground(java.awt.Color.lightGray);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel1.setText("CCS Library");

        jbLogin.setBackground(new java.awt.Color(51, 51, 255));
        jbLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbLogin.setForeground(new java.awt.Color(255, 255, 255));
        jbLogin.setText("LOGIN");
        jbLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLoginActionPerformed(evt);
            }
        });

        jButtonExit.setBackground(new java.awt.Color(255, 102, 102));
        jButtonExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonExit.setForeground(new java.awt.Color(255, 255, 255));
        jButtonExit.setText("EXIT");
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExit)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpSearch.setBackground(java.awt.Color.lightGray);

        jLabel2.setText("Search");

        jtSearchTittle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtSearchTittleActionPerformed(evt);
            }
        });

        jLabel3.setText("Title :");

        jLabel4.setText("Author:");

        jtSearchAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtSearchAuthorActionPerformed(evt);
            }
        });

        jbSearch.setText("SEARCH");
        jbSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpSearchLayout = new javax.swing.GroupLayout(jpSearch);
        jpSearch.setLayout(jpSearchLayout);
        jpSearchLayout.setHorizontalGroup(
            jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpSearchLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jpSearchLayout.createSequentialGroup()
                            .addGroup(jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addGap(32, 32, 32)
                            .addGroup(jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jtSearchTittle, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jtSearchAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(jLabel2))
                    .addComponent(jbSearch))
                .addGap(67, 67, 67))
        );
        jpSearchLayout.setVerticalGroup(
            jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtSearchTittle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtSearchAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbSearch)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jpBorrow.setBackground(java.awt.Color.lightGray);

        jLabel5.setText("Borrow");

        jtBorrowerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBorrowerNameActionPerformed(evt);
            }
        });

        jLabel6.setText("Name :");

        jLabel7.setText("Borrow date :");

        jtBorrowDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBorrowDateActionPerformed(evt);
            }
        });

        jLabel11.setText("Return date :");

        jtReturnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtReturnDateActionPerformed(evt);
            }
        });

        jbBorrow.setText("BORROW");
        jbBorrow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBorrowActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBorrowLayout = new javax.swing.GroupLayout(jpBorrow);
        jpBorrow.setLayout(jpBorrowLayout);
        jpBorrowLayout.setHorizontalGroup(
            jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBorrowLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jpBorrowLayout.createSequentialGroup()
                        .addGroup(jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11))
                        .addGap(32, 32, 32)
                        .addGroup(jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtBorrowDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtBorrowerName, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBorrowLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbBorrow)
                .addGap(64, 64, 64))
        );
        jpBorrowLayout.setVerticalGroup(
            jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBorrowLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtBorrowerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtBorrowDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBorrowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbBorrow)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jpBorrow1.setBackground(java.awt.Color.lightGray);

        jLabel8.setText("Return");

        jLabel9.setText("Name  :");

        jbReturnBook.setText("RETURN");
        jbReturnBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbReturnBookActionPerformed(evt);
            }
        });

        jtReturnName1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtReturnName1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpBorrow1Layout = new javax.swing.GroupLayout(jpBorrow1);
        jpBorrow1.setLayout(jpBorrow1Layout);
        jpBorrow1Layout.setHorizontalGroup(
            jpBorrow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBorrow1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jpBorrow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jpBorrow1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(61, 61, 61)
                        .addComponent(jtReturnName1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpBorrow1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbReturnBook)
                .addGap(66, 66, 66))
        );
        jpBorrow1Layout.setVerticalGroup(
            jpBorrow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpBorrow1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpBorrow1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jtReturnName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbReturnBook)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jTableAvailable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAvailable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableAvailableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableAvailable);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"All", "Business & Accounting", "Engineering", "Programming"}));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel13.setText("Category :");

        jTableBorrowed.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title", "Author", "Name", "Barrow date", "Return date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableBorrowed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBorrowedMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableBorrowed);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("BOOKS");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Borrowed Books");

        jLabel17.setText("Index :");

        jComboBoxIndex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"All","A", "B", "C", "D", "E", "F", "G", "I", "J", "K", "L", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }));
        jComboBoxIndex.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jComboBoxIndex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxIndexActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jpBorrow1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpBorrow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addGap(281, 281, 281))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 639, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel17)
                    .addComponent(jComboBoxIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpBorrow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jpBorrow1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtSearchTittleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtSearchTittleActionPerformed
       jbSearchActionPerformed(evt);
    }//GEN-LAST:event_jtSearchTittleActionPerformed

    private void jtSearchAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtSearchAuthorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtSearchAuthorActionPerformed

    private void jtBorrowerNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBorrowerNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtBorrowerNameActionPerformed

    private void jtBorrowDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBorrowDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtBorrowDateActionPerformed

    private void jtReturnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtReturnDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtReturnDateActionPerformed
    
    private void jbBorrowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBorrowActionPerformed
      borrowerName = jtBorrowerName.getText().trim();
     borrowDateString = jtBorrowDate.getText().trim();
     returnDateString = jtReturnDate.getText().trim();
    int selectedRow = jTableAvailable.getSelectedRow();
    Book foundBook = null;
    if (selectedRow != -1) {
        // Check if the fields are empty
        if (borrowerName.isEmpty() || borrowDateString.isEmpty() || returnDateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Check if the date formats are correct
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        dateFormatter.withResolverStyle(ResolverStyle.STRICT); // Ensure strict date parsing
        try {
            LocalDate borrowDate = LocalDate.parse(borrowDateString, dateFormatter);
            LocalDate returnDate = LocalDate.parse(returnDateString, dateFormatter);
            // Check if borrowDate is today
            if (!LocalDate.now().equals(borrowDate)) {
                JOptionPane.showMessageDialog(this, "Borrow date should be today.");
                return;
            }
 LocalDate today = LocalDate.now();
LocalDate maxReturnDate = today.plusDays(7);
if (returnDate.isBefore(today) || returnDate.isAfter(maxReturnDate)) {
    JOptionPane.showMessageDialog(this, "Return dates should fall within the range of this day and the next seven days.");
    return;
}

            // Find the book in the books list and set its availability to false
            DefaultTableModel modelAvailable = (DefaultTableModel) jTableAvailable.getModel();
            String title = (String) modelAvailable.getValueAt(selectedRow, 0);
            String author = (String) modelAvailable.getValueAt(selectedRow, 1);
            
            for (Book book : books) {
                if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                    book.setAvailable(false);
                    notAvailableBooks.add(book); // Add the book to the notAvailableBooks list
                    BorrowedBook borrowedBook = new BorrowedBook(title, author, borrowerName, borrowDate.toString(), returnDate.toString());
                    borrowedBooks1.add(borrowedBook); // Add the borrowed book to the list
                    foundBook = book;
                    break;
                }
            }
            

            // Update the table to reflect the changes
            modelAvailable.removeRow(selectedRow);

            // Display the borrowed book on jTableBorrowed
            DefaultTableModel modelBorrowed = (DefaultTableModel) jTableBorrowed.getModel();
            DefaultTableModel modeljTable2 = (DefaultTableModel) jTableBorrowed.getModel();
            modelBorrowed.addRow(new Object[]{title, author, borrowerName, borrowDate.toString(), returnDate.toString()});
            checkBorrowedBooksButtonActionPerformed(null);

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use MM/DD/YY.");
            return;
        }
          
         if (foundBook != null) {
            // Remove the book from the books list
            books.remove(foundBook);
            
         }
    }
    }//GEN-LAST:event_jbBorrowActionPerformed

    private void jTableAvailableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableAvailableMouseClicked
        // THIS IS THE CODE TO LOAD THE DATA TO THE JTEXTFIELDS
        DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
        int setSelectedRow = jTableAvailable.getSelectedRow();
   
    }//GEN-LAST:event_jTableAvailableMouseClicked
//to LoginForm
    private void jbLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLoginActionPerformed
    LoginForm loginForm = new LoginForm();
    loginForm.setVisible(true);
    
    }//GEN-LAST:event_jbLoginActionPerformed
//For Category
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
  String selectedCategory = (String) jComboBox1.getSelectedItem();

    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.setRowCount(0); // Clear previous table data

    ArrayList<Book> availableBooks = getAllAvailableBooks();
    for (Book book : books) {
        if (selectedCategory.equals("All") || selectedCategory.equals(book.getCategory())) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor(), true});
        Collections.sort(availableBooks, Comparator.comparing(Book::getTitle));
        }
        
    }
}

private boolean isBookBorrowed(String title, String author) {
    for (Book book : borrowedBooks) {
        if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
            return true;
        }
    }
    return false;
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jbSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSearchActionPerformed
    String searchTitle = jtSearchTittle.getText().toLowerCase();
    String searchAuthor = jtSearchAuthor.getText().toLowerCase();

    DefaultTableModel model = (DefaultTableModel) jTableAvailable. getModel();
    model.setRowCount(0); // Clear previous table data

    ArrayList<Book> allBooks = getAllAvailableBooks();
    for (Book book : allBooks) {
        if (book.getTitle().toLowerCase().contains(searchTitle) && book.getAuthor().toLowerCase().contains(searchAuthor) && !isBookBorrowed(book.getTitle(), book.getAuthor())) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor(), "Available"});
        }
    }
    }//GEN-LAST:event_jbSearchActionPerformed
public void setStaffForm(StaffForm staffForm) {
        this.staffForm = staffForm;
    }
    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
     // Write the data to a text file (notepad)
    String filePath = "C:\\Users\\NEIL\\Documents\\NetBeansProjects\\CCSLibrary\\src\\main\\java\\data\\BorrowedBooks.txt";
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
        DefaultTableModel modelBorrowed = (DefaultTableModel) jTableBorrowed.getModel();
        
        // Write table data
        for (int i = 0; i < modelBorrowed.getRowCount(); i++) {
            writer.println("Title: " + modelBorrowed.getValueAt(i, 0));
            writer.println("Author: " + modelBorrowed.getValueAt(i, 1));
            writer.println("Borrower Name: " + modelBorrowed.getValueAt(i, 2));
            writer.println("Borrow Date: " + modelBorrowed.getValueAt(i, 3));
            writer.println("Return Date: " + modelBorrowed.getValueAt(i, 4));
            writer.println("--------------------------");
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd-yyyy hh:mm a"));
        writer.println("Timestamp: " + timestamp);
        JOptionPane.showMessageDialog(this, "Data saved to file successfully.");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving data to file: " + ex.getMessage());
        ex.printStackTrace(); // Print the stack trace for debugging
    }

    // Exit the application
    System.exit(0);
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jbReturnBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbReturnBookActionPerformed
          borrowerName = jtReturnName1.getText().trim();

    int selectedRow = jTableBorrowed.getSelectedRow();
    if (borrowerName.isEmpty() && selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please enter the borrower's name and select a book to return.");
        return;
    }

    if (borrowerName.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please enter the borrower's name.");
        return;
    }

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a book to return.");
        return;
    }

    DefaultTableModel modelBorrowed = (DefaultTableModel) jTableBorrowed.getModel();
    String title = (String) modelBorrowed.getValueAt(selectedRow, 0);
    String author = (String) modelBorrowed.getValueAt(selectedRow, 1);
    String name = (String) modelBorrowed.getValueAt(selectedRow, 2);

    // Find the specific BorrowedBook object in borrowedBooks1
    BorrowedBook bookToRemove = null;
    for (BorrowedBook borrowedBook : borrowedBooks1) {
        if (borrowedBook.getTitle().equals(title) && borrowedBook.getAuthor().equals(author) && borrowedBook.getBorrowerName().equals(borrowerName)) {
            bookToRemove = borrowedBook;
            break;
        }
    }

    if (bookToRemove != null) {
        // Set the book's availability to true
        for (Book book : notAvailableBooks) {
            if (book.getTitle().equals(title) && book.getAuthor().equals(author)) {
                book.setAvailable(true);
                books.add(book);
                break;
            }
        }

        // Remove the book from borrowedBooks1
        borrowedBooks1.remove(bookToRemove);

        // Update the jTableBorrowed
        modelBorrowed.removeRow(selectedRow);

        // Add the book back to jTableAvailable
        DefaultTableModel modelAvailable = (DefaultTableModel) jTableAvailable.getModel();
        modelAvailable.addRow(new Object[]{title, author, name});

        JOptionPane.showMessageDialog(this, "Book returned successfully.");

        // Update the jTableAvailable
        updateTableModel();
    } else {
        JOptionPane.showMessageDialog(this, "Book not found in borrowed list.");
    }

}
    public JTable getJTableAvailable() {
        return jTableAvailable;
    }
  public void addBook(Book book) {
   System.out.println("Dashboard books size after before pare: " + books.size());

    availableBooks.add(book);
    books.add(book);
    DefaultTableModel modelAvailable = (DefaultTableModel) jTableAvailable.getModel();
    modelAvailable.addRow(new Object[]{book.getTitle(), book.getAuthor()});
    updateJTableAvailable();
    
    System.out.println("Dashboard books size after adding: " + books.size());
    // Print the available books
    printAvailableBooks();
}
    public void printAvailableBooks() {
    System.out.println("Available Books: books");
    for (Book book : books) {
        System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
         //System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
    }
}
    public void addNewBookToList(String title, String author, String category) {
    availableBooks.add(new Book(title, author, category, true));
}
  public void setJTableAvailableModel(DefaultTableModel model) {
    jTableAvailable.setModel(model);
}
 public void updateJTableAvailable(DefaultTableModel model) {
    DefaultTableModel modelAvailable = (DefaultTableModel) jTableAvailable.getModel();
    modelAvailable.setRowCount(0); // Clear existing rows

    for (int i = 0; i < model.getRowCount(); i++) {
        Object[] row = new Object[model.getColumnCount()];
        for (int j = 0; j < model.getColumnCount(); j++) {
            row[j] = model.getValueAt(i, j);
        }
        modelAvailable.addRow(row);
    }
}
 
public void updateJTableAvailable() {
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.setRowCount(0); // Clear existing rows

    for (Book book : availableBooks) {
        model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getName(), book.getBorrowDate(), book.getBorrowReturn()});
    }
}

//update the table available from staffForm
private void updateTableModel() {
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    model.setRowCount(0); // Clear the table

    // Sort the available books by title
    List<Book> availableBooks = getAllAvailableBooks();
    Collections.sort(availableBooks, Comparator.comparing(Book::getTitle));

    // Add sorted available books to the table
    for (Book book : availableBooks) {
        Object[] row = {book.getTitle(), book.getAuthor()};
        model.addRow(row);
    }
   
    }//GEN-LAST:event_jbReturnBookActionPerformed
public static Dashboard getInstance() {
    if (instance == null) {
        instance = new Dashboard();
    }
    return instance;
}
//checker if books are added from StaffForm
public void printBooks() {
    System.out.println("Books:");
    for (Book book : books) {
        System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
    }
}

public void addBookAndUpdateTable(String title, String author, String category) {
    // Add the book to the books ArrayList
    Book newBook = new Book(title, author, category); // Assuming Book class has a constructor that takes title and author
    books.add(newBook);

    // Update the jTableAvailable
    DefaultTableModel modelAvailable = (DefaultTableModel) jTableAvailable.getModel();
    modelAvailable.addRow(new Object[]{title, author});
}

// Add a method to update JTableAvailable with a new book
public void addBookToJTableAvailable(String title, String author) {
    DefaultTableModel modelAvailable = (DefaultTableModel) jTableAvailable.getModel();
    modelAvailable.addRow(new Object[]{title, author});
}

public void addBookFromStaffForm(String title, String author, String category, boolean available) {
    // Create a new Book instance
    Book newBook = new Book(title, author, category, available);

    // Add the new book to the Dashboard
    addBook(newBook);

    // Print all books including the newly added book
    System.out.println("All Books:STAFF");
    for (Book book : books) {
        System.out.println(book.getTitle() + " by " + book.getAuthor());
    }
}
public void setModeljTable2(DefaultTableModel model) {
    this.modeljTable2 = model;
}





    private void jtReturnName1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtReturnName1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtReturnName1ActionPerformed

    private void jComboBoxIndexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxIndexActionPerformed
     String selectedIndex = (String) jComboBoxIndex.getSelectedItem();
    filterTable(selectedIndex);
}

private void filterTable(String index) {
    DefaultTableModel model = (DefaultTableModel) jTableAvailable.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTableAvailable.setRowSorter(sorter);

    if ("All".equals(index)) {
        sorter.setRowFilter(null);
    } else {
        sorter.setRowFilter(RowFilter.regexFilter("^" + index, 0)); // Assuming the first column is the title column
    }
    }//GEN-LAST:event_jComboBoxIndexActionPerformed

    private void jTableBorrowedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBorrowedMouseClicked

    }//GEN-LAST:event_jTableBorrowedMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Dashboard dashboard = new Dashboard();
        StaffForm staffForm = new StaffForm();
        dashboard.setStaffForm(staffForm);
        staffForm.setDashboard(dashboard);
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
       
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBoxIndex;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTable jTableAvailable;
    private javax.swing.JTable jTableBorrowed;
    private javax.swing.JButton jbBorrow;
    private javax.swing.JButton jbLogin;
    private javax.swing.JButton jbReturnBook;
    private javax.swing.JButton jbSearch;
    private javax.swing.JPanel jpBorrow;
    private javax.swing.JPanel jpBorrow1;
    private javax.swing.JPanel jpSearch;
    private javax.swing.JTextField jtBorrowDate;
    private javax.swing.JTextField jtBorrowerName;
    private javax.swing.JTextField jtReturnDate;
    private javax.swing.JTextField jtReturnName1;
    private javax.swing.JTextField jtSearchAuthor;
    private javax.swing.JTextField jtSearchTittle;
    // End of variables declaration//GEN-END:variables

   
}
