package lu.etat.adapp_kmp.utils

import kotlinx.cinterop.*
import platform.darwin.*

actual object MemoryUtils {

    @OptIn(ExperimentalForeignApi::class)
    actual fun getUsedMemoryMb(): Double {
        val taskInfo = nativeHeap.alloc<mach_task_basic_info>()
        val count = nativeHeap.alloc<mach_msg_type_number_tVar>()
        count.value = (sizeOf<mach_task_basic_info>() / sizeOf<natural_tVar>()).toUInt()

        val result = task_info(
            mach_task_self_,
            MACH_TASK_BASIC_INFO.toUInt(),
            taskInfo.ptr.reinterpret(),
            count.ptr
        )

        val residentSize = if (result == KERN_SUCCESS)
            taskInfo.resident_size.toLong() / (1024.0 * 1024.0)
        else
            0.0

        nativeHeap.free(taskInfo)
        nativeHeap.free(count)

        return residentSize
    }
}