
function addVideoButton(name, image_url) {
    var textnode = document.createElement("button")
    textnode.innerHTML = name+" -> MP4 HIGH"
    textnode.setAttribute("id", "videobtn")  // #008000   #00FFFF  #FFFFFF
    textnode.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;background-image: url("+image_url+");background-repeat: no-repeat;background-position: left;padding-left: 250px;font-weight: 20vh;";
    document.getElementById("downloadbtn").appendChild(textnode)

    var textnode1 = document.createElement("button")
    textnode1.innerHTML = name+" -> MP4 LOW"
    textnode1.setAttribute("id", "videobtn")
    textnode1.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;background-image: url("+image_url+");background-repeat: no-repeat;background-position: left;padding-left: 250px;font-weight: 20vh;"
    document.getElementById("downloadbtn").appendChild(textnode1)

    var textnode2 = document.createElement("button")
    textnode2.innerHTML = name+" -> MP3"
    textnode2.setAttribute("id", "videobtn")
    textnode2.style.cssText = "text-align: center;border-color: blue;box-sizing: content-box;background-color: #FFFFFF;margin-left: auto;margin-right: auto;display: block;border-radius: 12px;font-size: 22px;cursor: pointer;background-image: url("+image_url+");background-repeat: no-repeat;background-position: left;padding-left: 250px;font-weight: 20vh;"
    document.getElementById("downloadbtn").appendChild(textnode2)

    var newline = document.createElement("br");
    newline.setAttribute("id", "videoln")
    document.getElementById("downloadbtn").appendChild(newline)
}


function removeVideoButtons() {
   var all_buttons = document.getElementById("downloadbtn")
   while (all_buttons.firstChild) {
       all_buttons.removeChild(all_buttons.lastChild)
   }
}

function updateDownloadProgress(percent) {
      document.getElementById("progress").innerHTML = percent
}
