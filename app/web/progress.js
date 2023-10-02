var ID = "";
var title = "";
var format = "";

function updateDownloadProgress(percent) {
      document.getElementById("progress").innerHTML = percent;
}

function addDownloadBtn(name, format) {
    var textnode = document.createElement("button");
    textnode.innerHTML = name+" | Click to Download "+format;
    textnode.setAttribute("id", "askdownload");
    textnode.setAttribute("onclick", "askServerToDownload()");
    textnode.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;";
    document.getElementById("filedownload").appendChild(textnode);

    var newline = document.createElement("br");
    newline.setAttribute("id", "videoln");
    document.getElementById("filedownload").appendChild(newline);
}

async function askServerToDownload() {

    if (ID === null || format === null || ID.length == 0 || format.length == 0) { return; }


    fetch("http://localhost:25533/ask?id="+ID+"&format="+format, {method: 'GET'})
      .then(response => {
        if (response.status === 200) {
          response.blob().then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = title+"."+format;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
          });
        } else {
          document.getElementById("askdownload").innerHTML = "Can't download file";
        }
      })
      .catch(error => {});

}

function checks_If_contains_ID(value) {
    for (var id of IDs) {
        if (id == value) {
            return true;
        }
    }
    return false;
}

function checks_If_contains_Title(value) {
    for (var title of titles) {
        if (title == value) {
            return true;
        }
    }
    return false;
}

function checks_If_contains_Format(value) {
    for (var format of formats) {
        if (format == value) {
            return true;
        }
    }
    return false;
}

function pollProgress() {
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
                ID = data["link"]
                title = data["title"];
                format = data["format"];
            }
            if ((data["stats"] != d) && (d == "Downloading...")) {
                addDownloadBtn(title, format);
            }
            updateDownloadProgress(data["stats"]);
        })
        .finally(() => {
            setTimeout(pollProgress, 1000);
        });
}


pollProgress();

