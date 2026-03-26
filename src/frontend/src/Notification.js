import {notification} from "antd";

/**
 * Opens a notification with the specified type and content.
 * Defaults to top-right placement if not specified.
 *
 * @param type the notification type ('success', 'error', 'info', 'warning')
 * @param message the notification title
 * @param description the notification description/content
 * @param placement the notification placement on screen (default: 'topRight')
 */
const openNotificationWithIcon = (type, message, description, placement) => {
    placement = placement || "topRight"
    notification[type]({message, description, placement});
};

/**
 * Displays a success notification.
 *
 * @param message the notification title
 * @param description the notification description/content
 * @param placement the notification placement on screen (optional)
 */
export const successNotification = (message, description, placement) =>
    openNotificationWithIcon('success', message, description, placement);

/**
 * Displays an error notification.
 *
 * @param message the notification title
 * @param description the notification description/content
 * @param placement the notification placement on screen (optional)
 */
export const errorNotification = (message, description, placement) =>
    openNotificationWithIcon('error', message, description, placement);

/**
 * Displays an info notification.
 *
 * @param message the notification title
 * @param description the notification description/content
 * @param placement the notification placement on screen (optional)
 */
export const infoNotification = (message, description, placement) =>
    openNotificationWithIcon('info', message, description, placement);

/**
 * Displays a warning notification.
 *
 * @param message the notification title
 * @param description the notification description/content
 * @param placement the notification placement on screen (optional)
 */
export const warningNotification = (message, description, placement) =>
    openNotificationWithIcon('warning', message, description, placement);