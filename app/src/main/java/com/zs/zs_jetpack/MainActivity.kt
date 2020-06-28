package com.zs.zs_jetpack

import android.os.Bundle
import com.zs.base_library.base.BaseVmActivity
import com.zs.base_library.common.stringForTime
import com.zs.zs_jetpack.play.AudioObserver
import com.zs.zs_jetpack.play.PlayList
import com.zs.zs_jetpack.play.PlayerManager
import com.zs.zs_jetpack.play.bean.AudioBean

/**
 * des 主页面，作用有二
 *     1.用于承载Fragment
 *     2.作为音频播放观察者,接受到通知立即更新viewModel内状态
 *       间接通过DataBinding更新View
 *
 * @author zs
 * @date 2020-05-12
 */
class MainActivity : BaseVmActivity() ,AudioObserver{

    private var playVM: PlayViewModel? = null

    override fun initViewModel() {
        playVM = getActivityViewModel(PlayViewModel::class.java)
    }

    override fun init(savedInstanceState: Bundle?) {
        PlayerManager.instance.register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        PlayerManager.instance.unregister(this)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_main
    }

    /**
     * 歌曲信息
     */
    override fun onAudioBean(audioBean: AudioBean) {
        playVM?.songName?.set(audioBean.name)
        playVM?.singer?.set(audioBean.singer)
        playVM?.maxDuration?.set(stringForTime(audioBean.duration))
        playVM?.maxProgress?.set(audioBean.duration)
        playVM?.albumPic?.set(audioBean.albumId)
    }

    /**
     * 播放状态-暂停/播放
     */
    override fun onPlaying(playing: Boolean) {
        playVM?.playStatus?.set(playing)
    }

    /**
     * 当前播放进度
     */
    override fun onProgress(currentDuration: Int, totalDuration: Int) {
        playVM?.currentDuration?.set(stringForTime(currentDuration))
        playVM?.playProgress?.set(currentDuration)
    }

    /**
     * 播放模式
     */
    override fun onPlayMode(playMode: Int) {
        when(playMode){
            PlayList.PlayMode.ORDER_PLAY_MODE ->  playVM?.playModePic?.set(R.mipmap.play_order)
            PlayList.PlayMode.SINGLE_PLAY_MODE ->  playVM?.playModePic?.set(R.mipmap.play_single)
            PlayList.PlayMode.RANDOM_PLAY_MODE ->  playVM?.playModePic?.set(R.mipmap.play_random)
        }
    }
}
