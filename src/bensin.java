/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import koneksi.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TegarDolkinem
 */
public class bensin extends javax.swing.JFrame {
//membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabe
    public String kode_transaksi, kode_pelanggan, kode_admin, nama_admin ,kode, bensin,jenis;
    int harga, uang, total, jumlah, kembalian,liter;
    /**
     * Creates new form bensin
     */
    public bensin() {
        initComponents();
        tampil_combo1();
        tampil_combo2();
        
        //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tabel_bensin.setModel(model);
        model.addColumn("KODE TRANSAKSI");
        model.addColumn("KODE PELANGGAN");
        model.addColumn("KODE ADMIN");
        model.addColumn("NAMA ADMIN");
        model.addColumn("KODE BENSIN");
        model.addColumn("JENIS BENSIN");
        model.addColumn("HARGA/LITER");
        model.addColumn("JUMLAH LITER");
        model.addColumn("TOTAL HARGA");
        model.addColumn("KEMBALIAN");
        //fungsi ambil data
        getDataProduk();
    }
    //fungsi membaca data kategori
    public void getDataProduk(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM bensin";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[10];
                obj[0]=res.getString("kode_transaksi");
                obj[1]=res.getString("kode_pelanggan");
                obj[2]=res.getString("kode_admin");
                obj[3]=res.getString("nama_admin");
                obj[4]=res.getString("kode_bensin");
                obj[5]=res.getString("jenis_bensin");
                obj[6]=res.getString("harga_bensin");
                obj[7]=res.getString("beli");
                obj[8]=res.getString("total_harga");
                obj[9]=res.getString("kembalian");
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void loadDataProduk(){
        //mengambil data dari textbox dan menyimpan nilainya pada variabel
        kode_transaksi = (kdt.getText());
        kode_pelanggan =(String) (kdpel.getSelectedItem());
        kode_admin = (String) (kdam.getSelectedItem());
        nama_admin = (nmad.getText());
        kode = (String) (kd_bensin.getSelectedItem());
        jenis = (jenis_bensin.getText());
        harga = Integer.parseInt(tharga.getText());
        liter = Integer.parseInt(tliter.getText());
        total = Integer.parseInt(ttotal.getText());
        kembalian = Integer.parseInt(tkembalian.getText());
       
    }
    
    public void dataSelect(){
        //deklarasi variabel
        int i = tabel_bensin.getSelectedRow();
        
        //uji adakah data di tabel?
        if(i == 1){
            //tidak ada yang terpilih atau dipilih.
            return;
        }
        kdt.setText(""+model.getValueAt(i,0));
        kdpel.setSelectedItem(""+model.getValueAt(i,1));
        kdam.setSelectedItem(""+model.getValueAt(i,2));
        nmad.setText(""+model.getValueAt(i,3));
        kd_bensin.setSelectedItem(""+model.getValueAt(i,4));
        jenis_bensin.setText(""+model.getValueAt(i,5));
        tharga.setText(""+model.getValueAt(i,6));
        tliter.setText(""+model.getValueAt(i,7));
        ttotal.setText(""+model.getValueAt(i,8));
        tkembalian.setText(""+model.getValueAt(i,9));
    }
    
    private void tampil_combo1(){
        try{
            Connection con = koneksi.getKoneksi();
            java.sql.Statement stt = con.createStatement();
            String sql= "select kode from tbl_pelanggan"; 
            java.sql.ResultSet res= stt.executeQuery(sql);
            while(res.next()){
                Object[]ob = new Object[20];
                ob[0]= res.getString(1);
                kdpel.addItem((String)ob[0]);
            }
            res.close();
            stt.close();
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    private void tampil_combo2(){
          try{
            Connection con = koneksi.getKoneksi();
            java.sql.Statement stt = con.createStatement();
            String sql= "select kode from tbl_admin"; 
            java.sql.ResultSet res= stt.executeQuery(sql);
            while(res.next()){
                Object[]ob = new Object[20];
                ob[0]= res.getString(1);
                kdam.addItem((String)ob[0]);
                nmad.setText((String) ob[1]);
            }
            res.close();
            stt.close();
            
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void reset(){
        kode_transaksi ="";
        kode_pelanggan ="";
        kode_admin="";
        nama_admin="";
        kode = "";
        harga = 0;
        uang = 0;
        total = 0;
        kdt.setText("");
        kdpel.setSelectedItem(kode_pelanggan);
        kdam.setSelectedItem(kode_admin);
        nmad.setText("");
        kd_bensin.setSelectedItem(kode);
        jenis_bensin.setText("");
        tharga.setText("");
        tliter.setText("");
        ttotal.setText("");
        tjumlah.setText("");
        tkembalian.setText("");
      
    }
    
    public void simpanDataProduk(){
         //panggil fungsi load data
        loadDataProduk();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO bensin(kode_transaksi,kode_pelanggan,kode_admin,nama_admin,kode_bensin,jenis_bensin, harga_bensin, beli, total_harga,kembalian)"
                            + "VALUES('"+kdt.getText()+"','"+kdpel.getSelectedItem()+"','"+kdam.getSelectedItem()+"','"+nmad.getText()+"','"+kd_bensin.getSelectedItem()+"','"+ jenis_bensin.getText() +"','"+ tharga.getText() +"','"+ tliter.getText() +"','"+ ttotal.getText() +"','"+tkembalian.getText()+"')";
            PreparedStatement p = (PreparedStatement) koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
          
            //ambil data
            getDataProduk();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void rubahDataProduk(){
          //panggil fungsi load data
        loadDataProduk();
        
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksi.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String sql  =   "UPDATE bensin SET kode_pelanggan = '"+ kdpel.getSelectedItem() +"',"
                            +"kode_admin= '"+kdam.getSelectedItem()+"',"
                            +"nama_admin= '"+nmad.getText()+"',"
                            +"jenis_bensin= '"+jenis_bensin.getText()+"',"
                            + "harga_bensin = '"+ tharga.getText() +"',"
                            + "liter  = '"+ tliter.getText() +"',"
                            + "total_harga  = '"+ ttotal.getText() +"',"
                            + "beli  = '"+ tjumlah.getText() +"',"
                            + "kembalian  = '"+ tkembalian.getText() +"'"
                            + "WHERE kode_transaksi ='" + kdt.getText() + "'";
            PreparedStatement p = (PreparedStatement) koneksi.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
            //ambil data
            getDataProduk();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void hapusDataProduk(){
        //panggil fungsi ambil data
        loadDataProduk(); 
        
        //Beri peringatan sebelum melakukan penghapusan data
        int pesan = JOptionPane.showConfirmDialog(null, "HAPUS DATA"+ kode_transaksi +"?","KONFIRMASI", JOptionPane.OK_CANCEL_OPTION);
        
        //jika pengguna memilih OK lanjutkan proses hapus data
        if(pesan == JOptionPane.OK_OPTION){
            //uji koneksi
            try{
                //buka koneksi ke database
                Statement stat = (Statement) koneksi.getKoneksi().createStatement();
                
                //perintah hapus data
                String sql = "DELETE FROM bensin WHERE kode_transaksi='"+ kode_transaksi +"'";
                PreparedStatement p =(PreparedStatement)koneksi.getKoneksi().prepareStatement(sql);
                p.executeUpdate();
                
                //fungsi ambil data
                getDataProduk();
                
                //fungsi reset data
                reset();
                JOptionPane.showMessageDialog(null, "SPBU SURAKARTA BERHASIL DIHAPUS");
            }catch(SQLException err){
                JOptionPane.showMessageDialog(null, err.getMessage());
            }
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tharga = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tliter = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tkembalian = new javax.swing.JTextField();
        tjumlah = new javax.swing.JTextField();
        ttotal = new javax.swing.JTextField();
        jenis_bensin = new javax.swing.JTextField();
        kd_bensin = new javax.swing.JComboBox<>();
        kdb = new javax.swing.JLabel();
        kdb1 = new javax.swing.JLabel();
        kdadm = new javax.swing.JLabel();
        nma = new javax.swing.JLabel();
        nmad = new javax.swing.JTextField();
        kdpel = new javax.swing.JComboBox<>();
        kdam = new javax.swing.JComboBox<>();
        kdt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_bensin = new javax.swing.JTable();
        trubah = new javax.swing.JButton();
        thapus = new javax.swing.JButton();
        thitung = new javax.swing.JButton();
        tproses = new javax.swing.JButton();
        tsimpan = new javax.swing.JButton();
        tkeluar = new javax.swing.JButton();
        new1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jLabel1.setText("SPBU KOTA SURAKARTA");

        jPanel1.setBackground(new java.awt.Color(51, 255, 255));

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel4.setText("JENIS BENSIN");

        jLabel7.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel7.setText("KODE BENSIN");

        jLabel6.setFont(new java.awt.Font("Arial Black", 0, 11)); // NOI18N
        jLabel6.setText("HARGA PER LITER");

        tharga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thargaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel5.setText("JUMLAH LITER");

        tliter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tliterActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel8.setText("TOTAL HARGA");

        jLabel9.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel9.setText("JUMLAH BAYAR");

        jLabel2.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        jLabel2.setText("KEMBALIAN");

        tkembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkembalianActionPerformed(evt);
            }
        });

        ttotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttotalActionPerformed(evt);
            }
        });

        kd_bensin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "B001", "B002", "B003", "B004" }));
        kd_bensin.setToolTipText("");
        kd_bensin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kd_bensinActionPerformed(evt);
            }
        });

        kdb.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        kdb.setText("KODE TRANSAKSI");

        kdb1.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        kdb1.setText("KODE PELANGGAN");

        kdadm.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        kdadm.setText("KODE ADMIN");

        nma.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        nma.setText("NAMA ADMIN");

        kdpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kdpelActionPerformed(evt);
            }
        });

        kdam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kdamMouseClicked(evt);
            }
        });
        kdam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kdamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tliter, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(tjumlah)
                            .addComponent(tkembalian)
                            .addComponent(ttotal)
                            .addComponent(tharga))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel7)
                                    .addComponent(kdb)
                                    .addComponent(kdb1)
                                    .addComponent(kdadm))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jenis_bensin, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                    .addComponent(nmad)
                                    .addComponent(kd_bensin, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kdam, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kdpel, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kdt))))
                        .addContainerGap(168, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nma)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kdb)
                    .addComponent(kdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kdb1)
                    .addComponent(kdpel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kdadm)
                    .addComponent(kdam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nma)
                    .addComponent(nmad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(kd_bensin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jenis_bensin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tliter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ttotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tkembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32))
        );

        tabel_bensin.setBackground(new java.awt.Color(0, 255, 255));
        tabel_bensin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_bensin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_bensinMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_bensin);

        trubah.setText("EDIT");
        trubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trubahActionPerformed(evt);
            }
        });

        thapus.setText("HAPUS");
        thapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thapusActionPerformed(evt);
            }
        });

        thitung.setText("HITUNG");
        thitung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thitungActionPerformed(evt);
            }
        });

        tproses.setText("PROSES");
        tproses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tprosesActionPerformed(evt);
            }
        });

        tsimpan.setText("SIMPAN");
        tsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsimpanActionPerformed(evt);
            }
        });

        tkeluar.setText("KELUAR");
        tkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkeluarActionPerformed(evt);
            }
        });

        new1.setText("NEW");
        new1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(jLabel1)
                        .addGap(0, 332, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(thapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(trubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(thitung)
                                    .addComponent(tproses))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(tkeluar)
                                    .addComponent(tsimpan))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(new1)
                                .addGap(8, 8, 8)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(trubah)
                            .addComponent(thitung)
                            .addComponent(tsimpan)
                            .addComponent(new1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(thapus)
                            .addComponent(tkeluar)
                            .addComponent(tproses))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void thargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thargaActionPerformed

    private void tliterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tliterActionPerformed

    }//GEN-LAST:event_tliterActionPerformed

    private void tkembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkembalianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkembalianActionPerformed

    private void ttotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttotalActionPerformed

    private void trubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trubahActionPerformed
        rubahDataProduk();
    }//GEN-LAST:event_trubahActionPerformed

    private void thapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thapusActionPerformed
        hapusDataProduk();
    }//GEN-LAST:event_thapusActionPerformed

    private void thitungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thitungActionPerformed
        int harga = Integer.parseInt(tharga.getText());

        int uang = Integer.parseInt(tliter.getText());

        int total = harga * uang;

        ttotal.setText(String.valueOf(total));

    }//GEN-LAST:event_thitungActionPerformed

    private void tprosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tprosesActionPerformed
        int total = Integer.parseInt(ttotal.getText());

        int jumlah = Integer.parseInt(tjumlah.getText());

        int kembalian = jumlah - total;

        tkembalian.setText(String.valueOf(kembalian));
    }//GEN-LAST:event_tprosesActionPerformed

    private void tsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsimpanActionPerformed
        simpanDataProduk();
    }//GEN-LAST:event_tsimpanActionPerformed

    private void tkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkeluarActionPerformed
        // TODO add your handling code here:
        utama f = new utama();
        f.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_tkeluarActionPerformed

    private void kd_bensinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kd_bensinActionPerformed
        // TODO add your handling code here:
        int harga = 0;
        if(kd_bensin.getSelectedItem()=="B001"){
            jenis = "PERTALITE";
            harga =8400;
        }
        else if(kd_bensin.getSelectedItem()=="B002"){
            jenis = "PERTAMAX";
            harga = 8600;
        }
        else if(kd_bensin.getSelectedItem()=="B003"){
            jenis = "SOLAR";
            harga = 7000;
        }
        else if(kd_bensin.getSelectedItem()=="B004"){
            jenis = "PREMIUM";
            harga = 7500;
        }
        else{
            harga = 0;
        }
        tharga.setText(String.valueOf(harga));
        jenis_bensin.setText(String.valueOf(jenis));
         
    }//GEN-LAST:event_kd_bensinActionPerformed

    private void tabel_bensinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_bensinMouseClicked
        // TODO add your handling code here:
        dataSelect();
    }//GEN-LAST:event_tabel_bensinMouseClicked

    private void new1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new1ActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_new1ActionPerformed

    private void kdpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kdpelActionPerformed
        // TODO add your handling code here:
        try{
            Connection con= koneksi.getKoneksi();
            java.sql.Statement stt = con.createStatement();
            String sql = "select* from tbl_pelanggan where kode='"+kdpel.getSelectedItem()+"'";
            java.sql.ResultSet res = stt.executeQuery(sql);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }//GEN-LAST:event_kdpelActionPerformed

    private void kdamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kdamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_kdamMouseClicked

    private void kdamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kdamActionPerformed
        // TODO add your handling code here:
        try{
            Connection con= koneksi.getKoneksi();
            java.sql.Statement stt = con.createStatement();
            String sql = "select* from tbl_admin where kode='"+kdam.getSelectedItem()+"'";
            java.sql.ResultSet res = stt.executeQuery(sql);
            while(res.next()){
                Object[] ob = new Object[3];
                ob[0]= res.getString(1);
                nmad.setText(res.getString("nama"));
                
            }
            res.close();
            stt.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_kdamActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(bensin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bensin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bensin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bensin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bensin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jenis_bensin;
    private javax.swing.JComboBox<String> kd_bensin;
    private javax.swing.JLabel kdadm;
    private javax.swing.JComboBox<String> kdam;
    private javax.swing.JLabel kdb;
    private javax.swing.JLabel kdb1;
    private javax.swing.JComboBox<String> kdpel;
    private javax.swing.JTextField kdt;
    private javax.swing.JButton new1;
    private javax.swing.JLabel nma;
    private javax.swing.JTextField nmad;
    private javax.swing.JTable tabel_bensin;
    private javax.swing.JButton thapus;
    private javax.swing.JTextField tharga;
    private javax.swing.JButton thitung;
    private javax.swing.JTextField tjumlah;
    private javax.swing.JButton tkeluar;
    private javax.swing.JTextField tkembalian;
    private javax.swing.JTextField tliter;
    private javax.swing.JButton tproses;
    private javax.swing.JButton trubah;
    private javax.swing.JButton tsimpan;
    private javax.swing.JTextField ttotal;
    // End of variables declaration//GEN-END:variables
}
