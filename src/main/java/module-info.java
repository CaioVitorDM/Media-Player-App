module com.example.projetoteste {
    requires javafx.controls;
    requires javafx.fxml;
    requires jlayer;
    requires jaudiotagger;


    opens br.ufrn.imd to javafx.fxml;
    exports br.ufrn.imd;
    exports br.ufrn.imd.Controller;
}