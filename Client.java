
package Net1Part2_Chatting;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDate;
import static java.time.LocalDate.now;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static java.time.OffsetTime.now;
import static java.time.Year.now;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 */
public class Client extends javax.swing.JFrame {
    private Timer statusTimer;  // Declare the timer variable
    
private Timer loginTimer;  // لحساب الوقت المنقضي
private LocalDateTime loginStartTime; 
private Date lastLoginTime; // في أعلى الكلاس

private File selectedFileToSend = null;
/**
     * Creates new form Client
     */
    private Timer inactivityTimer;
private boolean isTimerRunning = false;


private List<String> chatMessages = new ArrayList<>();
private JTextArea chatTextArea;
// Add these fields
private long loginTime;  // Store the login time
private Timer timer;     // Timer to update the time elapsed

private void initializeInactivityTimer() {
    inactivityTimer = new Timer(30000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Status1.equals("Active")) {
                Status1 = "Away";
                StatusCombo.setSelectedItem(Status1);
                // تحديث الحالة في الـ online_user إذا لزم الأمر
                online_user.setModel(dlm);
            }
        }
    });
      inactivityTimer.setRepeats(true);
}





private void displayElapsedTime() {
    // Create a Timer that updates every second
    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the current time
            Date currentTime = new Date();
            
            // Calculate the difference in milliseconds
            long diffInMillis = currentTime.getTime() - lastLoginTime.getTime();
            
            // Convert milliseconds to seconds, minutes, and hours
            long seconds = diffInMillis / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            // Display the time elapsed in the JLabel
            String elapsedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            timeLabel.setText("Time elapsed: " + elapsedTime);
        }
    });
    
    // Start the timer
    timer.start();
}

// Example for auto-updating status based on inactivity
private void resetInactivityTimer() {
    // Restart timer that tracks user activity
    if (statusTimer != null) {
        statusTimer.stop();
    }
    statusTimer = new Timer(30000, e -> {
        // Automatically change status to "Away" after 30 seconds of inactivity
        Status1 = "Away";
        StatusCombo.setSelectedItem(Status1);
        online_user.setModel(dlm);
        try {
            updateStatus("Away");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    statusTimer.setRepeats(false);
    statusTimer.start();
}

  private void updateStatus(String statusMessage) throws UnknownHostException {
      // Find user in the list and update their status
    for (User u : users) {
        if (u.getName().equalsIgnoreCase(userName)) {
            u.setStatus(statusMessage);
            break;
        
        }}
  }
 private LocalDateTime sessionStartTime;
    private Duration lastSessionDuration;
    private String currentUsername;
   // private JTextField username;
    private JPasswordField password;



    DatagramSocket socket;
    String userName;
    String localIp;
    int localPort;
    String remotIp;
    int remotPort;
    InetAddress remot_IPAddress;
    byte[] S_buffer;
    DatagramPacket sendpacket;
    byte[] R_buffer;
    DatagramPacket receive_packet;
    boolean conn = false;
    boolean logedin = false;
     LinkedList<User> users = new LinkedList<>();       
  public Client() {
      
        initComponents();
         Remot_IP1.setVisible(true);
        Remot_Port1.setVisible(true);
        textPaneArea.setEditable(false);
        Remot_IP1.setEditable(true);
        Remot_Port1.setEditable(true);
        inArea.setForeground(Color.GRAY);
        inArea.setText("enter text here");
        userName = "";
        localIp = "";
        localPort = 0;
        remotIp = "";
        remotPort = 0;
        R_buffer = new byte[50];
        receive_packet = new DatagramPacket(R_buffer, R_buffer.length);
         
        
        initializeInactivityTimer();
        
      
         
    }
  
    

    private boolean validateLogin(String usernameInput, String passwordInput) {
        return usernameInput.equals("admin") && passwordInput.equals("admin123");
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
        username = new javax.swing.JTextField();
        login = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        inArea = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        serIp = new javax.swing.JTextField();
        serPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Local_IP = new javax.swing.JTextField();
        Local_Port = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        online_user = new javax.swing.JList<>();
        jLabel9 = new javax.swing.JLabel();
        Send = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        status = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        textPaneArea = new javax.swing.JTextPane();
        sendAll = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        pass = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Remot_IP1 = new javax.swing.JTextField();
        Remot_Port1 = new javax.swing.JTextField();
        StatusCombo = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        filePortField = new javax.swing.JTextField();
        timeLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1015, 523));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setMaximumSize(new java.awt.Dimension(1050, 479));
        jPanel1.setMinimumSize(new java.awt.Dimension(992, 479));
        jPanel1.setPreferredSize(new java.awt.Dimension(1090, 560));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username :");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 90, 27));

        username.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        username.setToolTipText("");
        username.setPreferredSize(new java.awt.Dimension(7, 28));
        username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameActionPerformed(evt);
            }
        });
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, 180, -1));

        login.setBackground(new java.awt.Color(204, 255, 255));
        login.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        login.setForeground(new java.awt.Color(51, 51, 51));
        login.setText("Login");
        login.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        login.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });
        jPanel1.add(login, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 80, 80, -1));

        jButton2.setBackground(new java.awt.Color(204, 255, 255));
        jButton2.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Logout");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 80, 90, -1));

        inArea.setColumns(20);
        inArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        inArea.setForeground(new java.awt.Color(204, 204, 204));
        inArea.setLineWrap(true);
        inArea.setRows(5);
        inArea.setText("enter text here");
        inArea.setWrapStyleWord(true);
        inArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                inAreaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                inAreaFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(inArea);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, 380, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TCP Server Port :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 130, 27));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Avilable Interfaces ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 160, 24));

        serIp.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        serIp.setToolTipText("");
        serIp.setPreferredSize(new java.awt.Dimension(7, 28));
        serIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serIpActionPerformed(evt);
            }
        });
        jPanel1.add(serIp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 160, 23));

        serPort.setBackground(new java.awt.Color(204, 204, 255));
        serPort.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        serPort.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        serPort.setText("8000");
        serPort.setToolTipText("");
        serPort.setPreferredSize(new java.awt.Dimension(7, 28));
        serPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serPortActionPerformed(evt);
            }
        });
        jPanel1.add(serPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 160, 23));

        jLabel4.setFont(new java.awt.Font("Gill Sans MT", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TCP Server IP :");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 140, 24));

        jComboBox1.setFont(new java.awt.Font("Gill Sans MT", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Wifi", "Ethernet:169.254.49.56", "Loopback Pseudo-Interface 1:127.0.0.1", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 270, 30));

        jLabel5.setFont(new java.awt.Font("Gill Sans MT", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Local Port :");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 100, 27));

        jLabel6.setFont(new java.awt.Font("Gill Sans MT", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Local IP :");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 80, 24));

        Local_IP.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Local_IP.setToolTipText("");
        Local_IP.setPreferredSize(new java.awt.Dimension(7, 28));
        Local_IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Local_IPActionPerformed(evt);
            }
        });
        jPanel1.add(Local_IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 160, 23));

        Local_Port.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Local_Port.setToolTipText("");
        Local_Port.setPreferredSize(new java.awt.Dimension(7, 28));
        Local_Port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Local_PortActionPerformed(evt);
            }
        });
        jPanel1.add(Local_Port, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 160, 23));

        jLabel7.setFont(new java.awt.Font("Gill Sans MT", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Remote IP :");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 100, 24));

        jLabel8.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Your Status");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 100, 27));

        online_user.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        online_user.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                online_userValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(online_user);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 150, 240, 310));

        jLabel9.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Online Users ");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 120, 110, 20));

        Send.setBackground(new java.awt.Color(204, 255, 255));
        Send.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        Send.setForeground(new java.awt.Color(51, 51, 51));
        Send.setText("Send");
        Send.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        Send.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendActionPerformed(evt);
            }
        });
        jPanel1.add(Send, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 450, 100, 30));

        jLabel10.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("  Status :");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 60, 27));

        status.setBackground(new java.awt.Color(255, 204, 255));
        status.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        status.setToolTipText("");
        status.setPreferredSize(new java.awt.Dimension(7, 28));
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jPanel1.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 440, 380, -1));

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        textPaneArea.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        textPaneArea.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        textPaneArea.setFocusCycleRoot(false);
        jScrollPane4.setViewportView(textPaneArea);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, 360, 220));

        sendAll.setBackground(new java.awt.Color(204, 255, 255));
        sendAll.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        sendAll.setForeground(new java.awt.Color(51, 51, 51));
        sendAll.setText("Send All");
        sendAll.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, null, null, new java.awt.Color(204, 204, 204)));
        sendAll.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sendAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendAllActionPerformed(evt);
            }
        });
        jPanel1.add(sendAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 450, 100, 30));

        jLabel11.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("password :");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 50, 90, 27));

        pass.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pass.setToolTipText("");
        pass.setPreferredSize(new java.awt.Dimension(7, 28));
        jPanel1.add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, 180, -1));

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Gill Sans MT", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Chat");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 120, -1));

        Remot_IP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Remot_IP1ActionPerformed(evt);
            }
        });
        jPanel1.add(Remot_IP1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 160, -1));
        jPanel1.add(Remot_Port1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 160, -1));

        StatusCombo.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        StatusCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Away", "Busy" }));
        StatusCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusComboActionPerformed(evt);
            }
        });
        jPanel1.add(StatusCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 310, 150, 40));

        jLabel13.setFont(new java.awt.Font("Gill Sans MT", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Remote Port :");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 100, 27));

        jButton1.setBackground(new java.awt.Color(204, 255, 255));
        jButton1.setFont(new java.awt.Font("Gill Sans MT", 0, 12)); // NOI18N
        jButton1.setText("Save Chat");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 40, -1, -1));
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        jButton3.setBackground(new java.awt.Color(204, 255, 255));
        jButton3.setFont(new java.awt.Font("Gill Sans MT", 1, 14)); // NOI18N
        jButton3.setText("Attached File");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 130, -1));

        jLabel16.setText("Massage");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 80, 30));

        jLabel17.setText("File Port");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 390, 50, 20));

        filePortField.setText("9000");
        jPanel1.add(filePortField, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 390, 110, 20));

        timeLabel.setBackground(new java.awt.Color(255, 255, 255));
        timeLabel.setText("Login Time :");
        jPanel1.add(timeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 270, 30));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1120, 690);

        jLabel14.setForeground(new java.awt.Color(255, 204, 204));
        getContentPane().add(jLabel14);
        jLabel14.setBounds(20, 0, 1100, 550);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    DefaultListModel dlm;
    DataInputStream dataFromServer;
    DataInputStream DataInputStream;
    DataOutputStream dataToServer;
    Socket serverSocket;
    Read r;
    receive channel;
    boolean t = false;
    boolean j = false;
    String Status1="Active";

   
    
    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
                                         
                                      

    // ✅ التحقق من الحقول الأساسية فقط (بدون File Port)
    if (username.getText().equals("") || serIp.getText().equals("") || Local_IP.getText().equals("")
            || Local_Port.getText().equals("") || serPort.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "You must enter all fields: TCP Server IP & Port, Local IP & Port, and Username.");
        return;
    }

    // ✅ إذا لم يكن المستخدم مسجلاً الدخول بالفعل
    if (!logedin) {
        userName = username.getText().trim();
        String TcpIP = serIp.getText();
        int TcpPort = Integer.parseInt(serPort.getText().trim());
        String localIP = Local_IP.getText();
        localPort = Integer.parseInt(Local_Port.getText().trim());
        conn = true;
        localIp = localIP;
        
        loginStartTime = LocalDateTime.now();
if (loginTimer != null && loginTimer.isRunning()) {
    loginTimer.stop();
}
loginTimer = new Timer(1000, new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        Duration sessionDuration = Duration.between(loginStartTime, LocalDateTime.now());
        long hours = sessionDuration.toHours();
        long minutes = sessionDuration.toMinutes() % 60;
        long seconds = sessionDuration.getSeconds() % 60;
        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeLabel.setText("Login Time: " + formattedTime);
    }
});
loginTimer.start();
        try {
            socket = new DatagramSocket(localPort);
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            File f = new File("accounts.txt");
            BufferedReader freader = new BufferedReader(new FileReader(f));
            String s1;
            while ((s1 = freader.readLine()) != null) {
                s1 = s1.toUpperCase();
                String[] st = s1.split(" ");
                if (st[0].equals(username.getText().toUpperCase()) && st[1].equals(pass.getText().toUpperCase())) {
                    serverSocket = new Socket(InetAddress.getByName(TcpIP), TcpPort, InetAddress.getByName(localIP), localPort);
                    dataFromServer = new DataInputStream(serverSocket.getInputStream());
                    dataToServer = new DataOutputStream(serverSocket.getOutputStream());
                    dataToServer.writeUTF(userName + Status1);

                    DataInputStream = new DataInputStream(serverSocket.getInputStream());
                    String s = DataInputStream.readUTF();

                    if (s.equals("founded")) {
                        JOptionPane.showMessageDialog(null, "You are already logged in!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        username.setText("");
                        pass.setText("");
                        Local_Port.setText("");
                        return;
                    } else if (s.equals("accept")) {
                        dlm = new DefaultListModel();
                        online_user.setModel(dlm);
                        r = new Read(userName);
                        r.start();
                    }

                    j = true;
                    channel = new receive(this);
                    channel.start();
                    t = true;

                    if (!s.equals("founded")) {
                        JOptionPane.showMessageDialog(null, "You are logged in successfully");
                        logedin = true;

                        // ✅ تشغيل FileReceiverThread فقط إذا تم إدخال بورت
                        String filePortStr = filePortField.getText();
                        if (!filePortStr.isEmpty()) {
                            try {
                                int fileListenPort = Integer.parseInt(filePortStr);
                                new FileReceiverThread(fileListenPort).start();
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "⚠ Invalid file port. File receiving will not work.");
                            }
                        }
                    }

                    // ✅ عرض آخر وقت دخول إذا كان موجودًا
                    boolean found = false;
                    for (User u : users) {
                        if (username.getText().equalsIgnoreCase(u.getName())) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            if (u.getLastActiveDate() != null) {
                                String formattedDateTime = u.getLastActiveDate().format(formatter);
                                status.setText("Last login: " + formattedDateTime);

                                Duration duration = Duration.between(u.getLastActiveDate(), LocalDateTime.now());
                                long seconds = duration.getSeconds();
                                long minutes = seconds / 60;
                                long hours = minutes / 60;
                                minutes = minutes % 60;
                                seconds = seconds % 60;

                                status.setText(status.getText() + " (Time elapsed: " + String.format("%02d:%02d:%02d", hours, minutes, seconds) + ")");
                            } else {
                                status.setText("No previous login time found.");
                            }
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        User newUser = new User(username.getText(), LocalDateTime.now());
                        users.add(newUser);
                    }

                    // ✅ حفظ وقت الدخول في ملف
                    saveLoginInfoToFile(userName, LocalDateTime.now());
                    break;
                }
            }

            if (!logedin) {
                JOptionPane.showMessageDialog(null, "Invalid login: Username or password incorrect.");
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "⚠ The local port is already in use or unavailable.");
        }

    } else {
        JOptionPane.showMessageDialog(null, "You are already logged in.");
    }

    }//GEN-LAST:event_loginActionPerformed

    
   

    
    
    
   

    
    
    
    
    public void addChatMessage(String message) {
    chatMessages.add(message);  // Add to list for internal storage
    chatTextArea.append(message + "\n");  // Append to text area for display
}
private void saveLoginInfoToFile(String userName, LocalDateTime lastLoginTime) { 
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("chat_history.txt", true))) {
            writer.write(userName + " " + lastLoginTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.flush();
            writer.close();
    } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
     } 

    private void sendMessageToServer(String statusUpdateMessage) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    class Read extends Thread {

        String userName;

        public Read(String userName) {
            this.userName = userName;

        }

        public void run() {
            while (j) {
                try {
                   
                    String inputData = dataFromServer.readUTF();
                    if (inputData.equals("logout")) {
                        break;
                    }
                    if (inputData.contains("add to list")) {
                        inputData = inputData.substring(11);
                        dlm.clear();
                        StringTokenizer st = new StringTokenizer(inputData, "&?");
                        while (st.hasMoreTokens()) {
                            String line = st.nextToken();
                            String[] tokens = line.split(",");
                            
                                String element = new String(tokens[0]+","+tokens[2] + "," + tokens[1]);
                                dlm.addElement(element);
                            
                        }
                    }
                } catch (IOException ex) {

                }

            }
        }
    }

    private void serIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serIpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serIpActionPerformed

    private void serPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serPortActionPerformed

    private void Local_IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Local_IPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Local_IPActionPerformed

    private void Local_PortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Local_PortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Local_PortActionPerformed

    private void SendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendActionPerformed
    
    try {
        if (!conn) {
            JOptionPane.showMessageDialog(null, "You can't send, please login first");
            return;
        }

        if ((Remot_IP1.getText().isEmpty() || Remot_Port1.getText().isEmpty()) && selectedFileToSend == null) {
            JOptionPane.showMessageDialog(null, "You must select a user or file to send");
            return;
        }

        String msg = inArea.getText().trim();
        boolean hasText = !msg.isEmpty() && !msg.equals("enter text here");
        boolean hasFile = selectedFileToSend != null;

        if (!hasText && !hasFile) {
            JOptionPane.showMessageDialog(null, "You can't send an empty message and no file selected");
            return;
        }

        userName = username.getText();
        remotIp = Remot_IP1.getText().trim();
        remotPort = Integer.parseInt(Remot_Port1.getText().trim());
        remot_IPAddress = InetAddress.getByName(remotIp);

        String myIP = Local_IP.getText().trim();
        String myPort = Local_Port.getText().trim();
        if (remotIp.equals(myIP) && String.valueOf(remotPort).equals(myPort)) {
            JOptionPane.showMessageDialog(null, "❌ Can't send message or file to yourself.");
            return;
        }

        if (hasText) {
            StyledDocument doc = textPaneArea.getStyledDocument();
            Style style = textPaneArea.addStyle("", null);
            StyleConstants.setForeground(style, Color.RED);
            StyleConstants.setBackground(style, Color.WHITE);
            LocalDateTime now = LocalDateTime.now();

            String displayText = String.format("[%s] ME: %s From %d\n",
                    now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")),
                    msg, localPort);
            doc.insertString(doc.getLength(), displayText, style);

            String packetMsg = userName + ": " + msg;
            S_buffer = packetMsg.getBytes();
            sendpacket = new DatagramPacket(S_buffer, S_buffer.length, remot_IPAddress, remotPort);
            socket.send(sendpacket);

            inArea.setText("");
        }

        if (hasFile) {
            try (Socket fileSocket = new Socket(remotIp, Integer.parseInt(filePortField.getText()));
                 FileInputStream fis = new FileInputStream(selectedFileToSend);
                 OutputStream os = fileSocket.getOutputStream();
                 DataOutputStream dos = new DataOutputStream(os)) {

                long startTime = System.nanoTime();
                long totalBytesSent = 0;
                int packetCount = 0;
                List<Long> packetTimes = new ArrayList<>();

                dos.writeUTF(selectedFileToSend.getName());
                dos.writeLong(selectedFileToSend.length());

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                    dos.flush();
                    totalBytesSent += bytesRead;
                    packetCount++;
                    packetTimes.add(System.nanoTime());
                }

                long endTime = System.nanoTime();
                double elapsedSeconds = (endTime - startTime) / 1_000_000_000.0;

                double jitterSum = 0;
                for (int i = 1; i < packetTimes.size(); i++) {
                    jitterSum += Math.abs((packetTimes.get(i) - packetTimes.get(i - 1)) / 1_000_000.0);
                }
                double averageJitter = (packetCount > 1) ? jitterSum / (packetCount - 1) : 0;

                StyledDocument doc = textPaneArea.getStyledDocument();
                Style style = textPaneArea.addStyle("FileSent", null);
                StyleConstants.setForeground(style, Color.BLUE);
                StyleConstants.setBold(style, true);

                LocalDateTime now = LocalDateTime.now();
                String info = String.format("[%s] \uD83D\uDCE4 File sent: %s to %s:%d\n" +
                                "\uD83D\uDCC6 Packets: %d | \uD83D\uDCC1 Size: %.2f KB\n" +
                                "\u23F1 Time: %.2f sec | \uD83D\uDD39 Jitter: %.2f ms\n\n",
                        now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")),
                        selectedFileToSend.getName(), remotIp, remotPort,
                        packetCount, totalBytesSent / 1024.0,
                        elapsedSeconds, averageJitter);
                doc.insertString(doc.getLength(), info, style);

                selectedFileToSend = null;
            } catch (IOException | BadLocationException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "❌ Error sending file: " + ex.getMessage());
            }
        }
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage());
    }


    }//GEN-LAST:event_SendActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusActionPerformed

    private void inAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inAreaFocusGained
        // TODO add your handling code here:
        if (inArea.getText().equals("enter text here")) {
            inArea.setText("");
            inArea.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_inAreaFocusGained

    private void inAreaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inAreaFocusLost
        // TODO add your handling code here:
        if (inArea.getText().isEmpty()) {
            inArea.setForeground(Color.GRAY);
            inArea.setText("enter text here");
        }
    }//GEN-LAST:event_inAreaFocusLost
public void updateLastActive(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
               
                user.setLastActiveTime(LocalDateTime.now()); // Update to current time
                System.out.println("Updated last active date and time for: " + name);
                return; // Exit the method if the user is found and updated
            }
        }
       User u = new User(name,LocalDateTime.now());
       users.add(u);
       
    }
    
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (logedin) {
         String ss= username.getText();
         updateLastActive(ss);
         status.setText("");
            JOptionPane.showMessageDialog(null, "You are loged out successfully");
            logedin = false;
            t = false;
            j = false;
            if (loginTimer != null) {
    loginTimer.stop();
    timeLabel.setText("Login Time:");
}

            try {
                int localPort1 = Integer.parseInt(Local_Port.getText());
                String localIp1 = Local_IP.getText();
                InetAddress remot_IPAddress1 = InetAddress.getByName(localIp1);
                String msg = "logout";
                S_buffer = msg.getBytes();
                sendpacket = new DatagramPacket(S_buffer, S_buffer.length, remot_IPAddress1, localPort1);
                socket.send(sendpacket);
                closeSocket(socket);

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "logout";
            try {
                dataToServer.writeUTF(s);

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            dlm.clear();

            try {
                socket.close();
                serverSocket.close();
                DataInputStream.close();
                dataFromServer.close();
                dataToServer.close();

            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JOptionPane.showMessageDialog(null, "You are already loged out");
        }
        
        
        

    }//GEN-LAST:event_jButton2ActionPerformed

public static void closeSocket(DatagramSocket socket) {
    if (socket != null && !socket.isClosed()) {
        socket.close();
        System.out.println("Socket closed successfully");
    }
}
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void online_userValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_online_userValueChanged
        // TODO add your handling code here:
        try {
            int x = online_user.getModel().getSize();
            if (!evt.getValueIsAdjusting() && x != 0) {
                String s = (online_user.getSelectedValue().toString());
                String[] tokens = s.split(",");
                Remot_IP1.setText(tokens[1]);
                Remot_Port1.setText(tokens[2]);
            }
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_online_userValueChanged

    private void sendAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendAllActionPerformed
    
    try {
        if (!conn) {
            JOptionPane.showMessageDialog(null, "You can't send, please login first");
            return;
        }

        if ((inArea.getText().equals("") || inArea.getText().equals("enter text here")) && selectedFileToSend == null) {
            JOptionPane.showMessageDialog(null, "You can't send an empty message or no file selected");
            return;
        }

        int x = online_user.getModel().getSize();
        if (x == 0) return;

        for (int index = 0; index < x; index++) {
            ListModel model = online_user.getModel();
            String s = model.getElementAt(index).toString();
            String[] tokens = s.split(",");

            String targetIP = tokens[1].trim();
            String targetPort = tokens[2].trim();

            String myIP = Local_IP.getText().trim();
            String myPort = Local_Port.getText().trim();
            if (targetIP.equals(myIP) && targetPort.equals(myPort)) {
            continue;
}


            userName = username.getText();
            remotIp = targetIP;
            remotPort = Integer.parseInt(targetPort);
            remot_IPAddress = InetAddress.getByName(remotIp);

            // Send text message
            if (!inArea.getText().equals("") && !inArea.getText().equals("enter text here")) {
                String msg = inArea.getText();

                StyledDocument doc = textPaneArea.getStyledDocument();
                Style style = textPaneArea.addStyle("", null);
                StyleConstants.setForeground(style, Color.RED);
                StyleConstants.setBackground(style, Color.white);

                LocalDateTime now = LocalDateTime.now();
                String s1 = String.format("[%s] ME: %s From %s to %s\n",
                        now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")),
                        msg, localPort, targetPort);
                doc.insertString(doc.getLength(), s1, style);

                msg = userName + ": " + msg;
                S_buffer = msg.getBytes();
                sendpacket = new DatagramPacket(S_buffer, S_buffer.length, remot_IPAddress, remotPort);
                socket.send(sendpacket);
            }

            // Send file if exists
            if (selectedFileToSend != null) {
                try (Socket fileSocket = new Socket(remotIp, Integer.parseInt(filePortField.getText()));
                     FileInputStream fis = new FileInputStream(selectedFileToSend);
                     OutputStream os = fileSocket.getOutputStream();
                     DataOutputStream dos = new DataOutputStream(os)) {

                    long startTime = System.nanoTime();
                    long totalBytesSent = 0;
                    int packetCount = 0;
                    List<Long> packetTimes = new ArrayList<>();

                    dos.writeUTF(selectedFileToSend.getName());
                    dos.writeLong(selectedFileToSend.length());

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, bytesRead);
                        dos.flush();
                        totalBytesSent += bytesRead;
                        packetCount++;
                        packetTimes.add(System.nanoTime());
                    }

                    long endTime = System.nanoTime();
                    double elapsedSeconds = (endTime - startTime) / 1_000_000_000.0;
                    double jitterSum = 0;
                    for (int i = 1; i < packetTimes.size(); i++) {
                        jitterSum += Math.abs((packetTimes.get(i) - packetTimes.get(i - 1)) / 1_000_000.0);
                    }
                    double averageJitter = (packetCount > 1) ? jitterSum / (packetCount - 1) : 0;

                    StyledDocument doc = textPaneArea.getStyledDocument();
                    Style style = textPaneArea.addStyle("FileSent", null);
                    StyleConstants.setForeground(style, Color.BLUE);
                    StyleConstants.setBold(style, true);

                    LocalDateTime now = LocalDateTime.now();
                    String info = String.format("[%s] 📤 File sent: %s to %s:%s\n" +
                                    "📦 Packets: %d | 📁 Size: %.2f KB\n" +
                                    "⏱ Time: %.2f sec | 📉 Jitter: %.2f ms\n\n",
                            now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")),
                            selectedFileToSend.getName(), targetIP, targetPort,
                            packetCount, totalBytesSent / 1024.0,
                            elapsedSeconds, averageJitter);
                    doc.insertString(doc.getLength(), info, style);

                } catch (IOException | BadLocationException e) {
                    JOptionPane.showMessageDialog(null, "❌ Error sending file to " + remotIp + ": " + e.getMessage());
                }
            }
        }

        selectedFileToSend = null;
        inArea.setText("");

    } catch (Exception ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_sendAllActionPerformed

    private void Remot_IP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Remot_IP1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Remot_IP1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
         String selected = (String) jComboBox1.getSelectedItem();
                if ("Loopback Pseudo-Interface 1:127.0.0.1".equals(selected)) {
                  
                    serIp.setText("127.0.0.1");
                    Local_IP.setText("127.0.0.1");
                    
               } else if (jComboBox1.getSelectedItem().equals("Wifi")) {
    try {
        Local_IP.setText(InetAddress.getLocalHost().getHostAddress().toString());
    } catch (Exception e) {
        e.printStackTrace(); // أو طباعة رسالة خطأ مناسبة
    }
}

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void StatusComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusComboActionPerformed
     Status1 = (String) StatusCombo.getSelectedItem();
    updateUserStatusInList(userName, Status1); // Update the current user's status in the list
    sendStatusUpdateToServer(userName, Status1); // Notify other users
    resetInactivityTimer();
    }//GEN-LAST:event_StatusComboActionPerformed
private void sendStatusUpdateToServer(String username, String status) {
    String statusUpdateMessage = "status:" + username + ":" + status;
    sendMessageToServer(statusUpdateMessage);
}

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
         resetInactivityTimer(); 
    if (Status1.equals("Away")) {
        Status1 = "Active";
        StatusCombo.setSelectedItem(Status1);
        online_user.setModel(dlm);
    }
    
    }//GEN-LAST:event_jPanel1KeyPressed

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        resetInactivityTimer();

    // إذا كانت الحالة "Away"، قم بتحديثها إلى "Active"
    if (Status1.equals("Away")) {
        Status1 = "Active";
        StatusCombo.setSelectedItem(Status1); // تحديث عنصر القائمة المنسدلة
        online_user.setModel(dlm); // تحديث نموذج البيانات إذا لزم الأمر
    }
    }//GEN-LAST:event_jPanel1MouseClicked

    private void updateUserStatusInList(String username, String status) {
    boolean userFound = false;

    for (int i = 0; i < dlm.size(); i++) {
        String entry = (String) dlm.getElementAt(i);
        if (entry.startsWith(username + " (")) {
            // Update the user's status
            dlm.set(i, username + " (" + status + ")");
            userFound = true;
            break;
        }
    }

    if (!userFound) {
        // Add the user if not already in the list
        dlm.addElement(username + " (" + status + ")");
    }
}

    
    private void usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   // Fetch chat history
    List<String> chatHistory = getChatHistory();
    
    if (chatHistory.isEmpty()) {
        JOptionPane.showMessageDialog(null, "No chat history to save!", "Error", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Use JFileChooser to get the file path
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Chat History");
    int userSelection = fileChooser.showSaveDialog(null);
    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        // Write the chat history to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String message : chatHistory) {
                writer.write(message);
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Chat history exported successfully to: " + filePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting chat history: " + e.getMessage(),
                                          "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    }//GEN-LAST:event_jButton1ActionPerformed

  class FileReceiverThread extends Thread {
    private int listenPort;

    public FileReceiverThread(int listenPort) {
        this.listenPort = listenPort;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(listenPort)) {
            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());

                String fileName = dis.readUTF();
                long fileSize = dis.readLong();

                File receivedFile = new File("received_" + fileName);
                FileOutputStream fos = new FileOutputStream(receivedFile);

                long startTime = System.nanoTime();
                long totalBytes = 0;
                int packetCount = 0;
                List<Long> packetTimestamps = new ArrayList<>();

                byte[] buffer = new byte[4096];
                int bytesRead;
                while (fileSize > 0 && (bytesRead = dis.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    fileSize -= bytesRead;
                    totalBytes += bytesRead;
                    packetCount++;
                    packetTimestamps.add(System.nanoTime());
                }

                long endTime = System.nanoTime();
                double elapsedSeconds = (endTime - startTime) / 1_000_000_000.0;

                double jitterSum = 0;
                for (int i = 1; i < packetTimestamps.size(); i++) {
                    long diff = packetTimestamps.get(i) - packetTimestamps.get(i - 1);
                    jitterSum += Math.abs(diff / 1_000_000.0);
                }
                double avgJitter = (packetCount > 1) ? jitterSum / (packetCount - 1) : 0;

                fos.close();
                dis.close();
                socket.close();

                StyledDocument doc = textPaneArea.getStyledDocument();
                Style style = textPaneArea.addStyle("ReceiveLink", null);
                StyleConstants.setForeground(style, new Color(0, 128, 0));
                StyleConstants.setUnderline(style, true);
                StyleConstants.setBold(style, true);

                LocalDateTime now = LocalDateTime.now();
                String displayMessage = String.format(
                    "\n[%s] 📥 File received: %s\n📦 Packets: %d | 📁 Size: %.2f KB\n⏱ Time: %.2f sec | 📉 Jitter: %.2f ms\n\n",
                    now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")),
                    receivedFile.getName(),
                    packetCount,
                    totalBytes / 1024.0,
                    elapsedSeconds,
                    avgJitter
                );

                int startOffset = doc.getLength();
                doc.insertString(startOffset, displayMessage, style);
                int endOffset = doc.getLength();

                textPaneArea.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        int pos = textPaneArea.viewToModel2D(evt.getPoint());
                        if (pos >= startOffset && pos <= endOffset) {
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setSelectedFile(new File(receivedFile.getName()));
                            int userSelection = fileChooser.showSaveDialog(null);
                            if (userSelection == JFileChooser.APPROVE_OPTION) {
                                try {
                                    File destFile = fileChooser.getSelectedFile();
                                    Files.copy(receivedFile.toPath(), destFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                                } catch (IOException e) {
                                    JOptionPane.showMessageDialog(null, "❌ Error saving file: " + e.getMessage());
                                }
                            }
                        }
                    }
                });
            }
        } catch (IOException | BadLocationException e) {
            e.printStackTrace();
        }
    }
}

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

   
    JFileChooser fileChooser = new JFileChooser();
    int option = fileChooser.showOpenDialog(null);

    if (option == JFileChooser.APPROVE_OPTION) {
        selectedFileToSend = fileChooser.getSelectedFile();
        JOptionPane.showMessageDialog(null, "📁 File selected: " + selectedFileToSend.getName() + "\nPress SEND to transmit.");
    }

    }//GEN-LAST:event_jButton3ActionPerformed
    

   private List<String> getChatHistory() {
    StyledDocument doc = textPaneArea.getStyledDocument();
    List<String> history = new ArrayList<>();
    try {
        String text = doc.getText(0, doc.getLength());
        history.addAll(Arrays.asList(text.split("\n")));
    } catch (BadLocationException e) {
        e.printStackTrace();
    }
    return history;
}

    
    
    

    private Color getRandomColor() {
        // Generate a random color
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    private HashMap<Integer, Color> userColorMap = new HashMap<>();
 private Color getUserColor(int username) {
        // Check if the user already has a color assigned
        if (!userColorMap.containsKey(username)) {
            // Assign a random color for the user
            Color randomColor = getRandomColor();
            userColorMap.put(username, randomColor);
        }
        return userColorMap.get(username);}
 
  public void receive() {
    try {
        if (t) {
            StyledDocument doc = textPaneArea.getStyledDocument();
            Style style = textPaneArea.addStyle("", null);
            socket.receive(receive_packet);
            String msg = new String(R_buffer, 0, receive_packet.getLength());

            InetAddress senderIP = receive_packet.getAddress();
            int senderPort = receive_packet.getPort();

            // ✅ لا تستقبل الرسائل المرسلة من نفسك
            if (senderPort == localPort && senderIP.getHostAddress().equals(Local_IP.getText().trim())) return;

            if (msg.equals("logout")) return;

            StyleConstants.setForeground(style, getUserColor(senderPort));
            StyleConstants.setBackground(style, Color.white);

            LocalDateTime now = LocalDateTime.now();
            String s1 = "[" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")) + "] " + msg + " From " + senderPort + "\n";

            doc.insertString(doc.getLength(), s1, style);
            status.setText("Received From IP= " + senderIP.getHostAddress() + ", port: " + senderPort);
        }
    } catch (IOException | BadLocationException ex) {
        ex.printStackTrace();
    }
}


    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Client().setVisible(true);

        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Local_IP;
    private javax.swing.JTextField Local_Port;
    private javax.swing.JTextField Remot_IP1;
    private javax.swing.JTextField Remot_Port1;
    private javax.swing.JButton Send;
    private javax.swing.JComboBox<String> StatusCombo;
    private javax.swing.JTextField filePortField;
    private javax.swing.JTextArea inArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton login;
    private javax.swing.JList<String> online_user;
    private javax.swing.JTextField pass;
    private javax.swing.JButton sendAll;
    private javax.swing.JTextField serIp;
    private javax.swing.JTextField serPort;
    private javax.swing.JTextField status;
    private javax.swing.JTextPane textPaneArea;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables

}
