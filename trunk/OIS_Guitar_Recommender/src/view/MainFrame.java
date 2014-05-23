/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beans.Guitar_Description;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ois_guitar_recommender.Queries;
import ois_guitar_recommender.Recommender;

/**
 *
 * @author Krista
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private ArrayList<Guitar_Description> guitar_descs;
    private ArrayList<Guitar_Description> guitar_rec;
    Recommender recommender;

    public MainFrame(String frequenciesFile, int nrOfRecommendations) {
        initComponents();
        this.setTitle("OIS - Guitar Recommender");
        try {
            recommender = new Recommender(frequenciesFile, nrOfRecommendations);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> descriptions = Queries.getInstance().queryGuitarDescriptions();
        guitar_descs = new ArrayList<>();
        guitar_rec = new ArrayList<>();

        guitar_descs = buildGuitarList(descriptions);
        guitar_rec = buildGuitarList(recommender.recommend());

        buildPanel(guitarScroll, guitar_descs);
        buildPanel(recScroll, guitar_rec);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        guitarType = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        guitarScroll = new javax.swing.JScrollPane();
        recScroll = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setText("Search results");

        jLabel2.setText("Recommendations");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(guitarType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(searchButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(guitarScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(recScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(recScroll))
                    .addComponent(jSeparator3)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(guitarScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(guitarType)
                .addGap(1, 1, 1)
                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        String searchString = searchField.getText();

        ArrayList<GuitarDescriptionRank> ranks = new ArrayList<>();
        //   HashMap<Guitar_Description, Integer> results = new HashMap<>();
        for (Guitar_Description gd : guitar_descs) {
            int matching = gd.match(searchString);
            if (matching >= Math.ceil(searchString.split(" ").length / 2.0)) {
                ranks.add(new GuitarDescriptionRank(gd, matching));
            }
        }
        Collections.sort(ranks);

        guitar_descs.clear();
        guitar_rec.clear();

        for (GuitarDescriptionRank gdr : ranks) {
            guitar_descs.add(gdr.getGuitar());
        }
        buildPanel(guitarScroll, guitar_descs);
        ArrayList<String> descList = new ArrayList<>();
        for (Guitar_Description gd : guitar_descs) {
            descList.add(gd.getDesc());
        }

        recommender.addWitnessedDescriptions(descList);
        guitar_rec = buildGuitarList(recommender.recommend());

        buildPanel(recScroll, guitar_rec);
    }//GEN-LAST:event_searchButtonActionPerformed

    private void buildPanel(JScrollPane panel, ArrayList<Guitar_Description> descriptions) {
        JPanel guitarPanel = new JPanel();
        guitarPanel.setLayout(new BoxLayout(guitarPanel, BoxLayout.Y_AXIS));
        for (Guitar_Description gd : descriptions) {
            AbstractPanel p = new AbstractPanel(gd);
            guitarPanel.add(p);
        }
        panel.setViewportView(guitarPanel);
    }

    private ArrayList<Guitar_Description> buildGuitarList(ArrayList<String> descriptions) {
        ArrayList<Guitar_Description> list = new ArrayList<>();
        for (String desc : descriptions) {
            Guitar_Description gd = new Guitar_Description(desc);
            list.add(gd);
        }
        return list;
    }

    private static class GuitarDescriptionRank implements Comparable<GuitarDescriptionRank> {

        private Guitar_Description guitar;
        private int score;

        public GuitarDescriptionRank(Guitar_Description guitar, int score) {
            this.guitar = guitar;
            this.score = score;
        }

        public Guitar_Description getGuitar() {
            return guitar;
        }

        public int getScore() {
            return score;
        }

        public int compareTo(GuitarDescriptionRank other) {
            if (this.score > other.score) {
                return -1;
            } else if (this.score < other.score) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane guitarScroll;
    private javax.swing.JLabel guitarType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JScrollPane recScroll;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    // End of variables declaration//GEN-END:variables
}
