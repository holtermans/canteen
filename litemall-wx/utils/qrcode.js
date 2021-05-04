import drawQrcode from "./weapp.qrcode.esm"
function createQrcode(code) {

  const query = wx.createSelectorQuery()
  query.select('#myQrcode')
    .fields({
      node: true,
      size: true
    })
    .exec((res) => {
      var canvas = res[0].node

      // 调用方法drawQrcode生成二维码
      drawQrcode({
        canvas: canvas,
        canvasId: 'myQrcode',
        width: 260,
        padding: 30,
        background: '#ffffff',
        foreground: '#000000',
        text: code,
      })

      // 获取临时路径（得到之后，想干嘛就干嘛了）
      wx.canvasToTempFilePath({
        canvasId: 'myQrcode',
        canvas: canvas,
        x: 0,
        y: 0,
        width: 260,
        height: 260,
        destWidth: 260,
        destHeight: 260,
        success(res) {
          console.log('二维码临时路径：', res.tempFilePath)
        },
        fail(res) {
          console.error(res)
        }
      })
    })
}

export default createQrcode;