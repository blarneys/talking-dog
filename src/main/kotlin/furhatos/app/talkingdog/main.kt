package furhatos.app.talkingdog

import furhatos.app.talkingdog.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill

class TalkingdogSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
