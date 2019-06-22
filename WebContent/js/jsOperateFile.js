function createFile(fileName, data) {
  var urlObject = window.URL || window.webkitURL || window;
  var export_blob = new Blob([data]);
  var save_link = document.createElementNS("http://www.w3.org/1999/xhtml", "a")
  save_link.href = urlObject.createObjectURL(export_blob);
  save_link.download = fileName;
  var ev = document.createEvent("MouseEvents");
  ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
  save_link.dispatchEvent(ev);
}

