var image_url = "https://cdn.discordapp.com/attachments/779418496601686016/1156623723655348224/background.jpg?ex=6515a548&is=651453c8&hm=54a1d501eabd031cff961f91a8d087725cc4dbcf5b440eaae53358f1c54a80bc&";
var isButtonPresent = false;
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

    isButtonPresent = true;
}


function removeVideoButtons() {
   var all_buttons = document.getElementById("downloadbtn");
   while (all_buttons.firstChild) {
       all_buttons.removeChild(all_buttons.lastChild);
   }
}

async function getVideoTitle(link) {
    try {

           const response = await fetch("https://www.youtube.com/oembed?url="+link+"&format=json");

           if (!response.ok) {
                return "";
           }

           var data = await response.json();
           return data["title"];

        /*
           const html = await response.text();
           const doc = new DOMParser().parseFromString(html, 'text/html');
           const ytFormattedString = doc.querySelector('yt-formatted-string.style-scope.ytd-watch-metadata');

           if (ytFormattedString) {
                  return ytFormattedString.innerText.trim();
           } else {
                  return "Video has no title";
           }*/
    } catch (error) {
        return "";
    }
}

async function filterAndAddVideoButtons() {
    for (var link of document.getElementById("links").value.split("\n")) {
        if ((link.startsWith("https://www.youtube.com/watch?v=") || link.startsWith("https://youtu.be/")) && !isButtonPresent) {
            var title = await getVideoTitle(link);
            if (title.length > 0) {
                videos.set(title, link);
                addVideoButton(title);
            }
        }
    }
}

function updateDownloadProgress(percent) {
      document.getElementById("progress").innerHTML = percent;
}



function sendDownloadRequestmp4h() {
       var title = document.getElementById("mp4h").innerHTML;
       title = title.slice(0, (10));
       if (videos.has(title)) {
            var link = videos.get(title);

       }

}

function sendDownloadRequestmp4l() {
        return "";
}

function sendDownloadRequestmp3a() {
        return " ";
}
