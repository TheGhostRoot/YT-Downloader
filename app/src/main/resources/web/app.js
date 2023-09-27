
function test() {
    // Create an "li" node:
    //const node = document.createElement("button");

    // Create a text node:
    const textnode = document.createElement("button");

    // Append the text node to the "li" node:
    //node.appendChild(textnode);


    textnode.innerHTML = "Name of video -> 0%";

    // Append the "li" node to the list:
    document.getElementById("downloadbtn").appendChild(textnode);
}
