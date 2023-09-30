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
        .then(response => response.json())
        .then(data => {
            updateDownloadProgress(data+"%")
        })
        .finally(() => {
            setTimeout(pollProgress, 500);
        });
}


pollProgress();

