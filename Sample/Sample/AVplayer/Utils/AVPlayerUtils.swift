//
//  AVPlayerUtils.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/11.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import AVKit

class AVPlayerUtils: NSObject {

    public static func createPlayer(_ url:String) -> AVPlayer {
        return AVPlayer.init(url: URL.init(string: url)! as URL)
    }
    
    public static func createPlayer(urls:[String]) -> AVPlayer {
        let items = NSMutableArray.init()
        for url in urls {
            items.add(AVPlayerItem.init(url: URL.init(string: url)! as URL))
        }
        
        return AVQueuePlayer.init(items: items as! [AVPlayerItem])
    }
    

    
}
