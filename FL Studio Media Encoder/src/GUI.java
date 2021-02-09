import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class GUI implements ActionListener {
    // init buttons
    // JButton flstudioLocation = new JButton("Select FL Location");
    JButton selectoutput = new JButton("Select Output Location");
    JButton selectprojects = new JButton("Select Projects Folder");
    JButton encode = new JButton("Encode");

    // init frame
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();


    // init panels
    JPanel listpanel = new JPanel();
    JPanel buttonpanel = new JPanel();

    // init list
    DefaultListModel listModel = new DefaultListModel();
    JList projectlist = new JList(listModel);
    JScrollPane sp = new JScrollPane(projectlist);
    JLabel listlabel = new JLabel("Projects to be exported: ");

    // init combobox
    String[] comboboxoptions = {"Mp3", "WAV", "WAV & Mp3"};
    JComboBox Mp3orWAV = new JComboBox(comboboxoptions);

    // init logo
    // ImageIcon logo = new ImageIcon("D:\\FL STUDIO ENCODER\\logo.png");
    ImageIcon logoicon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));

    // RESCALE IMAGE
    Image logoimage = logoicon.getImage();
    Image logoimagescaled = logoimage.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

    ImageIcon logoicontodisplay = new ImageIcon(logoimagescaled);

    JLabel logolabel = new JLabel(logoicontodisplay);


    // Arraylist to store files picked
    String[] projectsList;


    // String to project input folder path
    String projectFolder;


    // String to output folder
    String outputFolder;


    // String containing path to FL Studio Folder
    String flstudioLocationString;

    public static void main(String[] args) {
        GUI gui = new GUI();

    }


    public GUI() {


        // change image label size


        // Test
        // init frame

        frame.setSize(700, 500);
        // panel.setBorder(BorderFactory.createEmptyBorder(600, 600, 600, 600));
        panel.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("FL Studio Media Encoder");

        panel.setBackground(Color.BLACK);

        // add panels to layout

        listpanel.setLayout(new BorderLayout());

        listpanel.setBackground(new Color(237, 237, 237));
        buttonpanel.setBackground(new Color(237, 237, 237));
        frame.add(listpanel, BorderLayout.CENTER);
        frame.add(buttonpanel, BorderLayout.PAGE_END);


        // add list
        // String[] data = {"one", "two", "three", "four"};

        projectlist.setPreferredSize(new Dimension(500, 200));
        listpanel.add(sp, BorderLayout.CENTER);

        // add label to list

        listpanel.add(listlabel, BorderLayout.PAGE_START);


        // buttonpanel.add(flstudioLocation);
        buttonpanel.add(selectprojects);
        buttonpanel.add(selectoutput);
        buttonpanel.add(encode);


        // add combobox

        buttonpanel.add(Mp3orWAV);


        // display logo

        listpanel.add(logolabel, BorderLayout.PAGE_END);



        // register actionlisteners for buttons
        selectprojects.addActionListener(this);
        encode.addActionListener(this);
        selectoutput.addActionListener(this);
        // flstudioLocation.addActionListener(this);

        // display gui
        // frame.pack();
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // HUB FOR ALL BUTTONS
        Object obj = e.getSource();
        if (obj == selectprojects) {
            selectProjectsMethod();
        } else if (obj == encode) {
            try {
                encodeMethod();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (obj == selectoutput) {
            selectOutput();
        }


    }

    // Method to select files to encode
    public void selectOutput() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            outputFolder = file.getPath();
            selectoutput.setText(projectFolder);


        }

    }



    // Method to select files to encode
    public void selectProjectsMethod() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            projectFolder = file.getPath();
            selectprojects.setText(projectFolder);


            // display projects found in folder in GUI list
            projectsList = file.list();

            for (String x : projectsList) {

                if (x.substring(x.length() - 3, x.length()).equals("flp")) {
                    listModel.addElement(x);

                }

            }

        }


    }


    // // Method to select FL Studio Location
    // public void flstudioLocationMethod() {
    // JFileChooser fileChooser = new JFileChooser();
    // fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    //
    // int option = fileChooser.showOpenDialog(frame);
    // if (option == JFileChooser.APPROVE_OPTION) {
    // File file = fileChooser.getSelectedFile();
    // projectFolder = file.getPath();
    // flstudioLocation.setText(projectFolder);
    //
    //
    // // set string to work with to FL Studio location
    // flstudioLocationString = file.getPath();
    //
    //
    // }
    //
    //
    // }



    public void encodeMethod() throws IOException {
        String comboselection = (String) Mp3orWAV.getSelectedItem();

        // String to contain CMD command section to specifiy output
        String exportType = null;

        switch (comboselection) {
            case ("Mp3"):
                exportType = "/Emp3";
                break;

            case ("WAV"):
                exportType = "/Ewav";
                break;

            case ("WAV & Mp3"):
                exportType = "/Emp3,wav";
                break;

        }
        // Process process = Runtime.getRuntime().exec("ping www.stackabuse.com");

        // ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd D:\\FL STUDIO ENCODER\\Projects && dir");

        // Read FL Studio program location from registry
        String Location = "SOFTWARE\\Image-Line\\Shared\\Paths";
        String Key = "FL Studio";
        flstudioLocationString = WindowsRegistrySnippet.readRegistry(Location, Key);


        // String that tells CMD to encode projects
        String cmdStr = "\"" + flstudioLocationString + "\" /R " + exportType + " /F" + "\"" + projectFolder + "\" ";
        cmdStr = cmdStr + "/O\"" + outputFolder + "\"";
        System.out.println(cmdStr);

        CMDPrompt.runCMD(cmdStr);


        // ProcessBuilder builder = new ProcessBuilder("cmd.exe", cmdStr.substring(1, 3), cmdStr);
        // builder.redirectErrorStream(true);
        // Process p = builder.start();


        // BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        // String line;
        // while (true) {
        // line = r.readLine();
        // if (line == null) {
        // break;
        // }
        // System.out.println(line);
        // }
    }

}

// "E:\FL STUDIO FOLDER\FL Studio 20\FL64.exe"/R/Emp3/F"D:\FL STUDIO ENCODER\Projects"
