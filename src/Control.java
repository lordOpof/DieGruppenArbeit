public class Control {
    Model m;
    UI ui;

    public Control(Model _m, UI _ui) {
        m = _m;
        ui = _ui;
        m.addSub(ui);
        ui.setArr(m.getArrayTest());
        ui.setRoCo();
        ui.setup(m.col, m.row);
        m.populateArr();
        ui.updateGrid();
        m.circler();
    }
}
