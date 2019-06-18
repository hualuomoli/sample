//
//  AppDelegate.swift
//  Sample
//
//  Created by 刘保全 on 2019/6/6.
//  Copyright © 2019 刘保全. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.makeKeyAndVisible()
        
//        window?.rootViewController = ThemeHomeViewController()
        
//        window?.rootViewController = HUDViewController()
//        window?.rootViewController = BlockViewController()
//        window?.rootViewController = AVplayerViewController()
//        window?.rootViewController = HandyJSONViewController()
//        window?.rootViewController = DemoPagePassDataHomeViewController()
        window?.rootViewController = ImageGifViewController()
        
        return true
    }


}

