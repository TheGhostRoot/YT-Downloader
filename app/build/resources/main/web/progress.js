var ID = "";
var title = "";
var format = "";

function updateDownloadProgress(percent) {
      document.getElementById("progress").innerHTML = percent;
}

function addDownloadBtn(name, format) {
    var textnode = document.createElement("button");
    textnode.innerHTML = name+" | Click to Download";
    textnode.setAttribute("id", "askdownload");
    textnode.setAttribute("onclick", "askServerToDownload("+name+", "+format+")");
    textnode.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;";
    document.getElementById("filedownload").appendChild(textnode);

    var newline = document.createElement("br");
    newline.setAttribute("id", "videoln");
    document.getElementById("downloadbtn").appendChild(newline);
}

async function askServerToDownload() {

    if (ID === null || format === null || ID.length == 0 || format.length == 0) { return; }

    const response = await fetch("http://localhost:25533/ask?id="+ID+"&format="+format, {method: 'GET'});
    addDownloadBtn(title, format);

    if (!response.ok) {
        document.getElementById("askdownload").innerHTML = "Can't download file";
    }
}

async function pollProgress() {
    fetch('http://localhost:25533/downloader', {
    method: "GET",
    headers: {
        "Accept": "*/*"
    }
    })
    .then(response => response.json())
    .then(async data => {
        var d = document.getElementById("progress").innerHTML;
        if (data["stats"] == "Downloading...") {
            ID = data["link"];
            title = data["title"];
            format = data["format"];
        }
        if ((data["stats"] != d) && (d == "Downloading...")) {
            await askServerToDownload(data["link"], data["format"], data["title"]);
        }
        updateDownloadProgress(data["stats"]);
    })
    .finally(() => {
        setTimeout(pollProgress, 1000);
    });
}


pollProgress();

