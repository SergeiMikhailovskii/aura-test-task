package com.mikhailovskii.aura.test.task.domain

import com.mikhailovskii.aura.test.task.BootData
import java.text.DateFormat
import java.util.Date


class BootsToBootSavedDataTypeMapper : Mapper<List<BootData>, BootSavedDataType> {
    override fun invoke(input: List<BootData>): BootSavedDataType {
        if (input.isEmpty()) {
            return BootSavedDataType.None
        } else if (input.size == 1) {
            val boot = input.first()
            val formattedDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
                .format(Date(boot.bootTime))
            return BootSavedDataType.Single(formattedDate)
        } else {
            val preLastBoot = input[input.size - 2]
            val lastBoot = input[input.size - 1]
            val delta = lastBoot.bootTime - preLastBoot.bootTime
            return BootSavedDataType.Multiple("$delta")
        }
    }
}