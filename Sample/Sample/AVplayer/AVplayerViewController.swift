//
//  AVplayerViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/11.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

class AVplayerViewController: UINavigationController {

    override func viewDidLoad() {
        super.viewDidLoad()

        
        
        
        let localController = AVPlayerNetworkViewController()
        
        self.pushViewController(localController, animated: true)
        
    }



}


