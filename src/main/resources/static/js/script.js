'use strict';

const imgContainer = document.querySelector('.img--container');

const timeout = function (s) {
  return new Promise(function (_, reject) {
    setTimeout(function () {
      reject(new Error(`Request took too long! Timeout after ${s} second`));
    }, s * 1000);
  });
};

const AJAX = async function (url) {
  try {
    const fetchData = fetch(url);
    const res = await Promise.race([fetchData, timeout(30)]);
    const data = await res.json();
    if (!res.ok) throw new Error(`${data.message} ${res.status}`);
    return data;
  } catch (err) {
    throw err;
  }
};

const init = async function(){
    const imgUrls = await AJAX("http://localhost:8080/view/images/flower_images");
    let html = '';
    for(let i = 0; i < imgUrls.length; i++){
        if(!imgUrls[i]) break;
        let src = `https://drive.google.com/uc?export=view&id=${imgUrls[i]}`;
        html+=`<img src=${src} alt="image">`;
    }
    imgContainer.innerHTML = html;
}

init();
