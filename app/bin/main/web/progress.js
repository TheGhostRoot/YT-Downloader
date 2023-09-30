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

async function askServerToDownload(name, format) {

    var link = videos.get(name);

    if (link.startsWith("https://www.youtube.com/watch?v=")) {
        link = link.substring(32, 43);
    } else if (link.startsWith("https://youtu.be/")) {
        link = link.substring(17, 28);
    }

    const response = await fetch("http://localhost:25533/ask");
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
    .then(response => response.text())
    .then(async data => {
        var splited = data.split("\n");
        if (data != document.getElementById("progress").innerHTML) {
            await askServerToDownload(splited[1], splited[2]);
        }
        updateDownloadProgress(splited[0]);
    })
    .finally(() => {
        setTimeout(pollProgress, 1000);
    });
}


pollProgress();

