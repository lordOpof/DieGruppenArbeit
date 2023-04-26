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
        /*m.arrayTest[0][0]=1;
        m.printArr();
        ui.updateGrid();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        m.add2simMeth(3, 4);
        ui.updateAtPos(3,4);
        m.printArr();*/
        m.circler();
    }
}
