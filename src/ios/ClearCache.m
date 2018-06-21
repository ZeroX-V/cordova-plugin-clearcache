#import "ClearCache.h"
#import <Cordova/CDV.h>

@interface ClearCache ()

@end

@implementation ClearCache

- (void)getCaches:(CDVInvokedUrlCommand *)command{
    NSString *cachesPath = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES)[0];
    NSArray *subPathArr = [[NSFileManager defaultManager] subpathsAtPath:cachesPath];
    NSString *filePath = nil;
    NSInteger totleSize = 0;
    for (NSString *subPath in subPathArr){
        filePath = [cachesPath stringByAppendingPathComponent:subPath];
        BOOL isDirectory = NO;
        BOOL isExist = [[NSFileManager defaultManager] fileExistsAtPath:filePath isDirectory:&isDirectory];
        if (!isExist || isDirectory || [filePath containsString:@".DS"]){
            continue;
        }
        NSDictionary *dict = [[NSFileManager defaultManager] attributesOfItemAtPath:filePath error:nil];
        NSInteger size = [dict[@"NSFileSize"] integerValue];
        totleSize += size;
    }
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsInt:totleSize];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)clearCaches:(CDVInvokedUrlCommand *)command{
    NSString *cachesPath = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES)[0];
    NSArray *subPathArr = [[NSFileManager defaultManager] contentsOfDirectoryAtPath:cachesPath error:nil];
    NSString *filePath = nil;
    NSError *error = nil;
    for (NSString *subPath in subPathArr) {
        filePath = [cachesPath stringByAppendingPathComponent:subPath];
        [[NSFileManager defaultManager] removeItemAtPath:filePath error:&error];
    }
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:@"true"];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end