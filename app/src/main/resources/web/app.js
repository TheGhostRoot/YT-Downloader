var image_url = "https://cdn.discordapp.com/attachments/779418496601686016/1156623723655348224/background.jpg?ex=6515a548&is=651453c8&hm=54a1d501eabd031cff961f91a8d087725cc4dbcf5b440eaae53358f1c54a80bc&";
var videos = new Map();

function addVideoButton(name) {
    var textnode = document.createElement("button");
    textnode.innerHTML = name+" | MP4 HIGH";
    textnode.setAttribute("id", "mp4h");
    textnode.setAttribute("onclick", "sendDownloadRequestmp4h()");
    textnode.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;";

    document.getElementById("downloadbtn").appendChild(textnode);

    var textnode1 = document.createElement("button");
    textnode1.innerHTML = name+" | MP4 LOW";
    textnode1.setAttribute("id", "mp4l");
    textnode1.setAttribute("onclick", "sendDownloadRequestmp4l()");
    textnode1.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;";
    document.getElementById("downloadbtn").appendChild(textnode1);

    var textnode2 = document.createElement("button");
    textnode2.innerHTML = name+" | MP3";
    textnode2.setAttribute("id", "mp3a");
    textnode2.setAttribute("onclick", "sendDownloadRequestmp3a()");
    textnode2.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;";
    document.getElementById("downloadbtn").appendChild(textnode2);

    var newline = document.createElement("br");
    newline.setAttribute("id", "videoln");
    document.getElementById("downloadbtn").appendChild(newline);
}


function removeVideoButtons() {
   var all_buttons = document.getElementById("downloadbtn");
   while (all_buttons.firstChild) {
       all_buttons.removeChild(all_buttons.lastChild);
   }
}

async function getVideoTitle(link) {
           const response = await fetch("https://www.youtube.com/oembed?url="+link+"&format=json");
           if (!response.ok) {
                return "";
           }
           var data = await response.json();
           return data["title"];
}

function isInVideos(link) {
    for (var v of videos.values()) {
        if (v == link) {
            return true;
        }
    }
    return false;
}

async function filterAndAddVideoButtons() {
    for (var link of document.getElementById("links").value.split("\n")) {
        if ((link.startsWith("https://www.youtube.com/watch?v=") || link.startsWith("https://youtu.be/")) && !isInVideos(link)) {
            var title = await getVideoTitle(link);
            if (title.length > 0) {
                videos.set(title, link);
                addVideoButton(title);
            }
        }
    }
}

function getLinkByTitle(title, n) {
      n *= -1;
      title = title.slice(0, n);
      return videos.has(title) ? videos.get(title) : "";
}

async function sendVideoToServer(youtube_link, download_format) {

               const response = await fetch('http://localhost:25533/download?link='+youtube_link+"&format="+download_format, {
                   method: 'POST'
               });
               return response;
}



async function sendDownloadRequestmp4h() {
       var youtube_link = getLinkByTitle(document.getElementById("mp4h").innerHTML, 11);
       if (youtube_link.length > 0) {
           const res = await sendVideoToServer(youtube_link, "mp4h");
      }
}

async function sendDownloadRequestmp4l() {
        var youtube_link = getLinkByTitle(document.getElementById("mp4l").innerHTML, 10);
        if (youtube_link.length > 0) {
            const res = await sendVideoToServer(youtube_link, "mp4l");
        }
}

async function sendDownloadRequestmp3a() {
        var youtube_link = getLinkByTitle(document.getElementById("mp3a").innerHTML, 6);
        if (youtube_link.length > 0) {
             const res = await sendVideoToServer(youtube_link, "mp3a");
        }
}
