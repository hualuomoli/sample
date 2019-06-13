//
//  AVPlayerView.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/11.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import AVKit

class AVPlayerView: UIView {

    @IBOutlet var contentView: UIView! // 一定要拖过来xib的view
    @IBOutlet weak var playerView: UIView!
    @IBOutlet weak var bottomView: UIView!
    @IBOutlet weak var pauseButton: UIButton!
    @IBOutlet weak var progressSlider: UISlider!
    @IBOutlet weak var rateButton: UIButton!
    @IBOutlet weak var definitionButton: UIButton!
    @IBOutlet weak var currentTimeLable: UILabel!
    @IBOutlet weak var totalTimeLable: UILabel!
    @IBOutlet weak var audioButton: UIButton!
    
    // -----------------------------------
    var player:AVPlayer! // 视频播放
    var playing:Bool = false
    var show:Bool = false
    var currentTime:Int = 0
    var lastSumTime:Int = 0
    var sumTime:Int = 0
    
    //MARK：实现初始化构造器
    //使用代码构造此自定义视图时调用
    override init(frame: CGRect) {       //每一步都必须
        super.init(frame: frame)         //实现父初始化
        contentView = loadViewFromNib()  //从xib中加载视图
        contentView.frame = bounds       //设置约束或者布局
        addSubview(contentView)          //将其添加到自身中
    }
    //可视化IB初始化调用
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        contentView = loadViewFromNib()
        contentView.frame = bounds
        addSubview(contentView)
        
    }
    
    
    //MARK：自定义方法
    func loadViewFromNib() -> UIView {
        //重点注意，否则使用的时候不会同步显示在IB中，只会在运行中才显示。
        //注意下面的nib加载方式直接影响是否可视化，如果bundle不确切（为nil或者为main）则看不到实时可视化
        let nib = UINib(nibName:String(describing: AVPlayerView.self), bundle: Bundle(for:AVPlayerView.self))//【？？？？】怎么将类名变为字符串：String(describing: MyView.self) Bundle的参数为type(of: self)也可以。
        let view = nib.instantiate(withOwner: self, options: nil)[0] as! UIView
        return view
    }

    
    func initPlayer(_ player:AVPlayer, _ total:Int) {
        self.addPlayer(player)
        
        // 暂停点击事件
        self.pauseButton.addTarget(self, action: #selector(togglePause), for: .touchUpInside)
        
        // 主页面点击事件
        let contentViewTap = UITapGestureRecognizer()
        contentViewTap.numberOfTapsRequired = 1
        contentViewTap.numberOfTouchesRequired = 1
        contentViewTap.addTarget(self, action: #selector(toggleShow))
        self.contentView.addGestureRecognizer(contentViewTap)
        
        // 监听播放进度
        self.player.addPeriodicTimeObserver(forInterval:CMTime.init(value: 1, timescale: 1), queue: DispatchQueue.main) { (time) in
            
            
            // 当前片段播放长度
            let current = Int(CMTimeGetSeconds(time))
            
            if(current == 0 && self.currentTime > 0) {
                // 切换片段,增加统计总时长
                self.lastSumTime = self.lastSumTime + self.currentTime
                self.currentTime = 0 // 重置为零
            } else {
                // 重新设置当前时间
                self.currentTime = current
            }
            // 总播放时长
            self.sumTime = self.lastSumTime + self.currentTime
            
            // 设置时间
            let currentMinutes = self.sumTime / 60
            let currentMillis = self.sumTime % 60
            let totalMinuts = total / 60
            let totalMillis = total % 60
            
            self.currentTimeLable.text = String.init(format: "%.2d:%.2d", currentMinutes, currentMillis)
            self.totalTimeLable.text = String.init(format: "%.2d:%.2d", totalMinuts, totalMillis)
            print("current:\(current), currentTime:\(self.currentTime), sumTime:\(self.sumTime)")
            
            let percent = (self.sumTime + self.currentTime) / total;
            self.progressSlider.setValue(Float(percent), animated: true)
            
            if(percent == 1) {
                self.end()
            }
        }
        
//        self.player.
        
//        self.player.end
        
        // 播放
        self.togglePause()
    }
    
    private func addPlayer(_ player:AVPlayer) {
        // 视频资源
        self.player = player
        
        // 播放图层
        let playerLayer = AVPlayerLayer.init(player: player)
        // resizeAspectFill 拉伸适配最短边
        // resizeAspect 拉伸适配最长边
        // resize 拉伸填充
        
        playerLayer.videoGravity = .resize
        // 大小
        playerLayer.frame = self.playerView.bounds
        // 添加到view的layer
        self.playerView.layer.addSublayer(playerLayer)
    }
    
    @objc func togglePause() {
        self.playing = !self.playing
        self.pauseButton.isSelected = !self.playing
        if(self.playing) {
            self.player.play()
        } else {
            self.player.pause()
        }
    }
    
    @objc func toggleShow() {
        self.bottomView.isHidden = !self.bottomView.isHidden
        
//        if(!self.bottomView.isHidden) {
//            DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 3) {
//                self.bottomView.isHidden = true
//            }
//        }
    }
    
    @objc func end() {
        print("end.")
    }

}
