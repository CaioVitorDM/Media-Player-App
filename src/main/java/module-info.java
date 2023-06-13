module br.ufrn.imd.Visao {
    requires javafx.controls;
    requires javafx.fxml;
    requires jlayer;
    requires jaudiotagger;


    opens br.ufrn.imd.Visao to javafx.fxml;
    exports br.ufrn.imd.Visao;
    exports br.ufrn.imd.Controller;
    opens br.ufrn.imd.Controller to javafx.fxml;
}