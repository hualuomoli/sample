//
//  ThemeUsePlistViewController.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/7.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit
import SwiftTheme

class ThemeUsePlistViewController: UIViewController {

    @IBOutlet weak var updateThemeButton: UIButton!
    
    var themes = ["theme_default","theme_dark", "theme_light"]
    var index:Int = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        ThemeManager.setTheme(plistName: themes[0], path: .mainBundle)
        
        self.view.theme_backgroundColor = "background_color"
        self.updateThemeButton.theme_tintColor = "button.title"
        self.updateThemeButton.theme_backgroundColor = "button.background"
        
        updateThemeButton.addTarget(self, action: #selector(updateTheme), for: .touchUpInside)
    }

    @objc func updateTheme() {
        index = (index + 1) % themes.count
        ThemeManager.setTheme(plistName: themes[index], path: .mainBundle)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        index = 0
        ThemeManager.setTheme(plistName: themes[index], path: .mainBundle)
    }

}
