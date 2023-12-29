tbl = "";       // ссылка на таблицу
mT = 0;         // размер таблицы
function mkTbl(tdSz, n) {                       // Функция создания таблицы
    mT = n;
    td = "<td style = 'background-color:#f0f0f0; width:" + tdSz + "; height:" + tdSz + "'";
    td += " onclick = 'sayRC(this)'></td>";
    tHdr = "<table id = 'tbl' style = 'background-color:#ccccaa; cursor:pointer'; border = 1px";
    document.write(tHdr);
    for (i = 0; i < mT; i++) {
        document.write("<tr>");
        for (j = 0; j < mT; j++) {
            document.write(td);
        }
        document.write("</tr>");
    }
    document.write("</table>");
    tbl = document.getElementById("tbl");
}
function sayRC(cll) {
    // Номер столбца текущей ячейки
    c = cll.cellIndex;
    r = gtRw(cll, c);
    alert("Ячейка " + r + ":" + c);
}
// Возвращает номер строки, которой расположена ячейка cll
function gtRw(cll, c) {
    for (i = 0; i < mT; i++) {
        rw = tbl.rows[i];
        if (rw.cells[c] == cll) return i;
    }
}
// Заполнение таблицы
function nGmFftngs() {
    // mT - размер заполняемой таблицы
    fL = mT * mT;
    // Массив генерируемых целых чисел
    arrF = new Array(fL);
    // Массив флажков генерируемых целых чисел
    // Элемент arrF2[k] равен false, если число k уже имеется в массиве arrF
    arrF2 = new Array(fL);
    for (k = 0; k < fL; k ++) {
        arrF[k] = 0;
        arrF2[k] = true;
    }
    kL = kL2 = 0;
    // Цикл заполнения массива arrF
    // Число итераций не более 10000
    while (kL < fL && kL2++ < 10000) {
        // Получаем целое случайное число из диапазона [1, fL]
        k = Math.floor(Math.random() * (fL + 1));
        if (k > 0 && arrF2[k - 1]) {
            arrF2[k - 1] = false;
            arrF[kL] = k;
            kL++;
        }
    }
    kL = 0;
    // Цикл заполнения таблицы числами из массива arrF
    for (i = 0; i < mT; i++) {
        rw = tbl.rows[i];
        for (j = 0; j < mT; j++) {
            k = arrF[kL++];
            rw.cells[j].innerHTML = (k > fL - 1) ? "" : k;
        }
    }
}