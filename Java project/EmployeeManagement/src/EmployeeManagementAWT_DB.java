import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeeManagementAWT_DB extends Frame implements ActionListener {
    
    Label companyLabel; 
    Label l1, l2, l3, l4;
    TextField t1, t2, t3, t4;
    Button addBtn, showBtn, updateBtn, deleteBtn, clearBtn;
    TextArea output;

    public EmployeeManagementAWT_DB() {
        
        setTitle("PSG Tech - Employee Management System ");
        setLayout(null);
        setBackground(new Color(220, 240, 255)); 

        // --- Get Screen Dimensions ---
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // --- Layout Constants ---
        int labelWidth = 100;
        int fieldWidth = 180;
        int height = 25;
        int yStep = 40;
        
        // Widths for Centering Calculations
        int inputGroupWidth = labelWidth + 10 + fieldWidth; // 290 pixels
        int companyLabelWidth = 300; 
        
        int btnWidth = 75;
        int btnGap = 10;
        int totalBtnWidth = 5 * btnWidth + 4 * btnGap; // 415 pixels

        // --- Master Horizontal Alignment Logic ---
        int screenCenter = screenWidth / 2;

        // --- Initialize Components (Same) ---
        companyLabel = new Label("PSG Tech");
        companyLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        l1 = new Label("ID:"); l2 = new Label("Name:"); l3 = new Label("Designation:"); l4 = new Label("Salary:");
        t1 = new TextField(); t2 = new TextField(); t3 = new TextField(); t4 = new TextField();
        
        addBtn = new Button("Add"); updateBtn = new Button("Update"); deleteBtn = new Button("Delete");
        showBtn = new Button("Show All"); clearBtn = new Button("Clear");
        
        output = new TextArea();
        output.setFont(new Font("Monospaced", Font.BOLD, 16));


        // --- 2. Set Bounds with Center-Aligned Logic ---
        
        // A. Company Label 
        int companyLabelHeight = 30;
        int companyLabelX = screenCenter - (companyLabelWidth / 2); 
        
        // *** KEY CHANGE: Increase Y position from 20 to 40 ***
        int companyLabelY = 40;
        companyLabel.setBounds(companyLabelX, companyLabelY, companyLabelWidth, companyLabelHeight);

        // B. Input Fields and Labels
        // ** Y-Start is adjusted based on the new company label position **
        int yStart = companyLabelY + companyLabelHeight + 30; 
        
        // X-coordinate for the start of the labels: Screen Center - Half of the Input Group Width
        int labelStartX = screenCenter - (inputGroupWidth / 2); 
        
        int fieldStartX = labelStartX + labelWidth + 10; 
        
        // Rows 1-4: Input Group Aligned
        l1.setBounds(labelStartX, yStart, labelWidth, height); 
        t1.setBounds(fieldStartX, yStart, fieldWidth, height);
        
        l2.setBounds(labelStartX, yStart + yStep, labelWidth, height); 
        t2.setBounds(fieldStartX, yStart + yStep, fieldWidth, height);
        
        l3.setBounds(labelStartX, yStart + 2 * yStep, labelWidth, height); 
        t3.setBounds(fieldStartX, yStart + 2 * yStep, fieldWidth, height);
        
        l4.setBounds(labelStartX, yStart + 3 * yStep, labelWidth, height); 
        t4.setBounds(fieldStartX, yStart + 3 * yStep, fieldWidth, height);

        // C. Buttons
        int btnY = yStart + 4 * yStep + 20;
        
        // Center the entire 415px button row
        int btnXStart = screenCenter - (totalBtnWidth / 2); 

        addBtn.setBounds(btnXStart, btnY, btnWidth, 30);
        updateBtn.setBounds(btnXStart + btnWidth + btnGap, btnY, btnWidth, 30);
        deleteBtn.setBounds(btnXStart + 2 * (btnWidth + btnGap), btnY, btnWidth, 30);
        showBtn.setBounds(btnXStart + 3 * (btnWidth + btnGap), btnY, btnWidth, 30);
        clearBtn.setBounds(btnXStart + 4 * (btnWidth + btnGap), btnY, btnWidth, 30);


        // D. Output Area (Dynamically sized, full-width)
        int outputX = 50;
        int outputY = btnY + 50; 
        int outputWidth = screenWidth - 100;
        int outputHeight = screenHeight - outputY - 80;

        output.setBounds(outputX, outputY, outputWidth, outputHeight);

        // --- Add components (Same) ---
        add(companyLabel);
        add(l1); add(l2); add(l3); add(l4);
        add(t1); add(t2); add(t3); add(t4);
        add(addBtn); add(updateBtn); add(deleteBtn); add(showBtn); add(clearBtn);
        add(output);

        // --- Event Listeners and Main Setup (Same) ---
        addBtn.addActionListener(this);
        updateBtn.addActionListener(this);
        deleteBtn.addActionListener(this);
        showBtn.addActionListener(this);
        clearBtn.addActionListener(this);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });
        
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setVisible(true);
    }
    
    // ... actionPerformed and helper methods remain the same ...
    public void actionPerformed(ActionEvent ae) {
        output.setText(""); 
        
        try {
            if (ae.getSource() == addBtn) {
                int id = safeParseInt(t1.getText());
                String name = t2.getText();
                String desig = t3.getText();
                double salary = safeParseDouble(t4.getText());
                
                if (name.trim().isEmpty() || desig.trim().isEmpty()) {
                    output.setText("Error: Name and Designation fields cannot be empty.");
                    return;
                }
                
                Employee e = new Employee(id, name, desig, salary);
                int status = EmployeeDAO.addEmployee(e);
                
                output.setText(status > 0 ? "Employee added successfully!" : "Error adding employee (ID might be duplicate).");
            } else if (ae.getSource() == updateBtn) {
                int id = safeParseInt(t1.getText());
                String name = t2.getText();
                String desig = t3.getText();
                double salary = safeParseDouble(t4.getText());
                
                Employee e = new Employee(id, name, desig, salary);
                int status = EmployeeDAO.updateEmployee(e);
                
                output.setText(status > 0 ? "Employee with ID " + id + " updated successfully!" : "Error: Employee with ID " + id + " not found or no changes made.");
            } else if (ae.getSource() == deleteBtn) {
                int id = safeParseInt(t1.getText()); 
                
                int status = EmployeeDAO.deleteEmployee(id);
                
                output.setText(status > 0 ? "Employee with ID " + id + " deleted successfully!" : "Error: Employee with ID " + id + " not found.");
            } else if (ae.getSource() == showBtn) {
                List<Employee> list = EmployeeDAO.getAllEmployees();
                output.setText(String.format("%-5s | %-20s | %-20s | %s\n", "ID", "Name", "Designation", "Salary"));
                output.append("---------------------------------------------------------------------------------------\n");
                if (list.isEmpty()) {
                     output.append("No employees found in the database.");
                } else {
                    for (Employee e : list)
                        output.append(String.format("%-5d | %-20s | %-20s | %.2f\n", e.id, e.name, e.designation, e.salary));
                }
            } else if (ae.getSource() == clearBtn) {
                t1.setText(""); t2.setText(""); t3.setText(""); t4.setText("");
                output.setText("Fields cleared.");
            }
        } catch (NumberFormatException e) {
            output.setText("Input Error: Please ensure ID and Salary are valid numbers.");
        } catch (Exception e) {
             output.setText("An unexpected error occurred: " + e.getMessage());
             e.printStackTrace();
        }
    }

    private int safeParseInt(String text) throws NumberFormatException {
        if (text.trim().isEmpty()) throw new NumberFormatException("ID cannot be empty.");
        return Integer.parseInt(text.trim());
    }

    private double safeParseDouble(String text) throws NumberFormatException {
        if (text.trim().isEmpty()) throw new NumberFormatException("Salary cannot be empty.");
        return Double.parseDouble(text.trim());
    }

    public static void main(String[] args) {
        new EmployeeManagementAWT_DB();
    }
}