//
//  ThemeViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/7.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import SwiftTheme

class ThemeViewController: UIViewController {

    var index:Int = 0
    
    @IBOutlet weak var updateThemeButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.view.theme_backgroundColor = ["#90caf9", "#ffeb3b"]
        self.updateThemeButton.theme_backgroundColor = ["#b39ddb", "#ffa726"]
        self.updateThemeButton.theme_tintColor = ["#84ffff", "#ba68c8"]
        
        self.updateThemeButton.addTarget(self, action: #selector(updateTheme), for: .touchUpInside)
    }

    @objc func updateTheme() {
        index = index + 1
        ThemeManager.setTheme(index: index % 2)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        index = 0
        ThemeManager.setTheme(index: index)
    }

    

}
