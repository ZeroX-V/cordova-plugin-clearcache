#import <Cordova/CDVPlugin.h>

@interface ClearCache : CDVPlugin

@property (nonatomic, copy) NSString *callbackId;

- (void)getCaches:(CDVInvokedUrlCommand*)command;

- (void)clearCaches:(CDVInvokedUrlCommand*)command;

@end