//
//  AVPlayerNetworkViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/11.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import AVKit

class AVPlayerNetworkViewController: UIViewController {
    
    @IBOutlet weak var playerView: AVPlayerView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // 视频资源
//        let player = AVPlayerUtils.createPlayer(AVPLAYER_MOVIE)
        let player = AVPlayerUtils.createPlayer(urls: AVPLAYER_MOVIES)
        playerView.initPlayer(player, 98)
        
    }
    
    
}
