/**
 * Uploader for PC
 * dropzone.js -- 弃用
 * @version 1.0
 */

var files = new Array;

Dropzone.autoDiscover = false;
var myDropzone = new Dropzone("#uploads", {
    url: "upload",
    autoProcessQueue: true,
    paramName : "file",
    maxFiles : 5,
    maxFilesize : 10,
    addRemoveLinks : true
    /*previewsContainer: '#template'*/
});

/*$("#btnUp").click(function () {
    myDropzone.processQueue();
});*/

$("#btnGet").click(function () {
    //console.log(myDropzone.getAcceptedFiles());
    console.log(files);
});

myDropzone.on("success", function(data) {
    var ret = data.xhr.responseText;
    files.push($.parseJSON(ret).url);
});
myDropzone.on("removedfile", function(data) {
    if(typeof(data.xhr) != "undefined"){
        var ret = data.xhr.responseText;
        var file = $.parseJSON(ret).url;
        for(var i=0; i<files.length; i++) {
            if(files[i] == file) {
                files.splice(i, 1);
                break;
            }
        }
    }
});