package ca.davidfuchs.jessedit;

import ca.davidfuchs.jessedit.ess.ESSFile;
import ca.davidfuchs.jessedit.ess.ESSReader;
import ca.davidfuchs.jessedit.ess.ESSUtils;
import ca.davidfuchs.jessedit.ess.StructRefId;
import org.apache.commons.cli.*;
import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class JESSEdit {
    private static final Logger logger = LoggerFactory.getLogger(JESSEdit.class);

    public static void main(String args[]) throws Exception {
        Options options = new Options();

        OptionGroup defaultOptions = new OptionGroup();
        defaultOptions.addOption(OptionBuilder.withLongOpt("help").withDescription("Print this help message.").create());
        defaultOptions.addOption(OptionBuilder.withLongOpt("version").withDescription("Print application version and exit.").create());


        OptionGroup optionGroupGUI = new OptionGroup();
        optionGroupGUI.addOption(OptionBuilder.withLongOpt("gui").withDescription("Run the JESSEdit GUI.").create());
        optionGroupGUI.addOption(OptionBuilder.withLongOpt("file").withDescription("ESS file to load.").create());

        OptionGroup optionGroupCLI = new OptionGroup();
        optionGroupCLI.addOption(OptionBuilder.withLongOpt("file").withDescription("ESS file to load.").create());

        options.addOptionGroup(defaultOptions);
        options.addOptionGroup(optionGroupGUI);
        options.addOptionGroup(optionGroupCLI);

        try {
            CommandLineParser parser = new BasicParser();
            CommandLine commandLine = parser.parse(options, args, true);

            if (commandLine.getOptions().length > 0) {
                if (commandLine.hasOption("version")) {
                    System.out.println("version 1.0");
                }

                if (commandLine.hasOption("help")) {
                    System.out.println("hep meh");
                    printHelp(options);
                }
            } else {
                if (commandLine.hasOption("gui")) {
                    logger.error("LOL.");
                } else {
                    if (commandLine.hasOption("file")) {
                        dumpESSFile(commandLine.getOptionValue("file"));
                    } else {
                        printHelp(options);
                    }
                }
            }
        } catch (ParseException parseException) {
            printHelp(options);
        }
    }

    private static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("JESSEdit", options);
    }

    private static void dumpESSFile(String fileName) throws IOException {
        InputStream inputStream = JESSEdit.class.getResourceAsStream("/autosave1.ess");

        ESSFile essFile = ESSReader.readESSFile(inputStream);

        for (int index = 0; index < 10; index++) {
            StructRefId refId = essFile.getChangeForms().get(index).getRefId();

            for (StructRefId id : essFile.getFormIdArray()) {
                if (refId.equals(id)) {
                    logger.info(String.format("%d: %s -> %s", index, refId.toString(), id.toString()));
                    break;
                }
            }
        }

        logger.info(essFile.getHeader().toString());


    }

    private static void showScreenshot(final ESSFile essFile) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);

                try {
                    UIManager.setLookAndFeel(SubstanceGraphiteLookAndFeel.class.getName());
                } catch (Exception e) {
                    // It doesn't matter if we can't set the look and feel.
                    e.printStackTrace();
                }

                JFrame jFrame = new JFrame();
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                ImagePanel imagePanel = new ImagePanel(ESSUtils.getScreenShot(essFile));
                jFrame.getContentPane().add(imagePanel);
                jFrame.getContentPane().setPreferredSize(imagePanel.getSize());
                jFrame.pack();

                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                jFrame.setLocation(dim.width / 2 - jFrame.getSize().width / 2, dim.height / 2 - jFrame.getSize().height / 2);

                jFrame.setVisible(true);
            }
        });
    }

    private static class ImagePanel extends JPanel {
        private BufferedImage bufferedImage;

        public ImagePanel(BufferedImage bufferedImage) {
            this.bufferedImage = bufferedImage;

            setSize(bufferedImage.getWidth() + 16, bufferedImage.getHeight() + 16);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bufferedImage, 8, 8, null);
        }
    }
}
