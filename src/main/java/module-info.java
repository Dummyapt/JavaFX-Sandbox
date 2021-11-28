module de.dummyapt.sandboxfx {
    requires javafx.controls;
    requires java.sql;

    exports de.dummyapt.sandbox;
    exports de.dummyapt.sandbox.pages;
    exports de.dummyapt.sandbox.calculator;
    exports de.dummyapt.sandbox.covidtable;
    exports de.dummyapt.sandbox.covidtable.database;
    exports de.dummyapt.sandbox.emschertrade;
    exports de.dummyapt.sandbox.emschertrade.database;
    exports de.dummyapt.sandbox.numberchain;
    exports de.dummyapt.sandbox.numberchain.observer;
    exports de.dummyapt.sandbox.tictactoe;
    exports de.dummyapt.sandbox.tictactoe.observer;
    exports de.dummyapt.sandbox.randomnumber;
    exports de.dummyapt.sandbox.moneyexchanger;
    exports de.dummyapt.sandbox.connectfour;
    exports de.dummyapt.sandbox.connectfour.observer;
}