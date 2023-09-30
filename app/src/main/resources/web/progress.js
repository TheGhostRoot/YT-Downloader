function updateDownloadProgress(percent) {
      document.getElementById("progress").innerHTML = percent;
}

function pollProgress() {
    fetch('http://localhost:25533/downloader', {
    method: "GET",
    headers: {
        "Accept": "*/*"
    }
    })
        .then(response => response.text())
        .then(data => {
            console.log(data);
            updateDownloadProgress(data);
        })
        .finally(() => {
            setTimeout(pollProgress, 1000);
        });
}


pollProgress();

