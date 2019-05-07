/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.document.library.kernel.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryService
 * @generated
 */
@ProviderType
public class DLFileEntryServiceWrapper
	implements DLFileEntryService, ServiceWrapper<DLFileEntryService> {

	public DLFileEntryServiceWrapper(DLFileEntryService dlFileEntryService) {
		_dlFileEntryService = dlFileEntryService;
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry addFileEntry(
			long groupId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, long fileEntryTypeId,
			java.util.Map
				<String, com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
					ddmFormValuesMap,
			java.io.File file, java.io.InputStream is, long size,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.addFileEntry(
			groupId, repositoryId, folderId, sourceFileName, mimeType, title,
			description, changeLog, fileEntryTypeId, ddmFormValuesMap, file, is,
			size, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileVersion
			cancelCheckOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.cancelCheckOut(fileEntryId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #checkInFileEntry(long, DLVersionNumberIncrease, String,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public void checkInFileEntry(
			long fileEntryId, boolean major, String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.checkInFileEntry(
			fileEntryId, major, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId,
			com.liferay.document.library.kernel.model.DLVersionNumberIncrease
				dlVersionNumberIncrease,
			String changeLog,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.checkInFileEntry(
			fileEntryId, dlVersionNumberIncrease, changeLog, serviceContext);
	}

	@Override
	public void checkInFileEntry(
			long fileEntryId, String lockUuid,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.checkInFileEntry(
			fileEntryId, lockUuid, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long fileEntryId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.checkOutFileEntry(
			fileEntryId, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry
			checkOutFileEntry(
				long fileEntryId, String owner, long expirationTime,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.checkOutFileEntry(
			fileEntryId, owner, expirationTime, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry copyFileEntry(
			long groupId, long repositoryId, long fileEntryId,
			long destFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.copyFileEntry(
			groupId, repositoryId, fileEntryId, destFolderId, serviceContext);
	}

	@Override
	public void deleteFileEntry(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.deleteFileEntry(fileEntryId);
	}

	@Override
	public void deleteFileEntry(long groupId, long folderId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.deleteFileEntry(groupId, folderId, title);
	}

	@Override
	public void deleteFileVersion(long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.deleteFileVersion(fileEntryId, version);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry
			fetchFileEntryByImageId(long imageId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.fetchFileEntryByImageId(imageId);
	}

	@Override
	public java.io.InputStream getFileAsStream(long fileEntryId, String version)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileAsStream(fileEntryId, version);
	}

	@Override
	public java.io.InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileAsStream(
			fileEntryId, version, incrementCounter);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getFileEntries(
				long groupId, long folderId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntries(
			groupId, folderId, status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getFileEntries(
				long groupId, long folderId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntries(
			groupId, folderId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getFileEntries(
				long groupId, long folderId, long fileEntryTypeId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntries(
			groupId, folderId, fileEntryTypeId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getFileEntries(
				long groupId, long folderId, String[] mimeTypes, int status,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntries(
			groupId, folderId, mimeTypes, status, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getFileEntries(
				long groupId, long folderId, String[] mimeTypes, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntries(
			groupId, folderId, mimeTypes, start, end, obc);
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId) {
		return _dlFileEntryService.getFileEntriesCount(groupId, folderId);
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId, int status) {
		return _dlFileEntryService.getFileEntriesCount(
			groupId, folderId, status);
	}

	@Override
	public int getFileEntriesCount(
		long groupId, long folderId, long fileEntryTypeId) {

		return _dlFileEntryService.getFileEntriesCount(
			groupId, folderId, fileEntryTypeId);
	}

	@Override
	public int getFileEntriesCount(
		long groupId, long folderId, String[] mimeTypes) {

		return _dlFileEntryService.getFileEntriesCount(
			groupId, folderId, mimeTypes);
	}

	@Override
	public int getFileEntriesCount(
		long groupId, long folderId, String[] mimeTypes, int status) {

		return _dlFileEntryService.getFileEntriesCount(
			groupId, folderId, mimeTypes, status);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry getFileEntry(
			long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntry(fileEntryId);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry getFileEntry(
			long groupId, long folderId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntry(groupId, folderId, title);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry
			getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getFileEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock getFileEntryLock(
		long fileEntryId) {

		return _dlFileEntryService.getFileEntryLock(fileEntryId);
	}

	@Override
	public int getFoldersFileEntriesCount(
		long groupId, java.util.List<Long> folderIds, int status) {

		return _dlFileEntryService.getFoldersFileEntriesCount(
			groupId, folderIds, status);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, long userId, long rootFolderId, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getGroupFileEntries(
			groupId, userId, rootFolderId, start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, long userId, long repositoryId, long rootFolderId,
				String[] mimeTypes, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getGroupFileEntries(
			groupId, userId, repositoryId, rootFolderId, mimeTypes, status,
			start, end, obc);
	}

	@Override
	public java.util.List<com.liferay.document.library.kernel.model.DLFileEntry>
			getGroupFileEntries(
				long groupId, long userId, long rootFolderId,
				String[] mimeTypes, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.document.library.kernel.model.DLFileEntry> obc)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getGroupFileEntries(
			groupId, userId, rootFolderId, mimeTypes, status, start, end, obc);
	}

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getGroupFileEntriesCount(
			groupId, userId, rootFolderId);
	}

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long repositoryId, long rootFolderId,
			String[] mimeTypes, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getGroupFileEntriesCount(
			groupId, userId, repositoryId, rootFolderId, mimeTypes, status);
	}

	@Override
	public int getGroupFileEntriesCount(
			long groupId, long userId, long rootFolderId, String[] mimeTypes,
			int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.getGroupFileEntriesCount(
			groupId, userId, rootFolderId, mimeTypes, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public boolean hasFileEntryLock(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.hasFileEntryLock(fileEntryId);
	}

	@Override
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.isFileEntryCheckedOut(fileEntryId);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isKeepFileVersionLabel(
			long fileEntryId, boolean majorVersion,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.isKeepFileVersionLabel(
			fileEntryId, majorVersion, serviceContext);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #isKeepFileVersionLabel(long, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public boolean isKeepFileVersionLabel(
			long fileEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.isKeepFileVersionLabel(
			fileEntryId, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry moveFileEntry(
			long fileEntryId, long newFolderId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.moveFileEntry(
			fileEntryId, newFolderId, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.lock.Lock refreshFileEntryLock(
			String lockUuid, long companyId, long expirationTime)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.refreshFileEntryLock(
			lockUuid, companyId, expirationTime);
	}

	@Override
	public void revertFileEntry(
			long fileEntryId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryService.revertFileEntry(
			fileEntryId, version, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.search(
			groupId, creatorUserId, status, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
			long groupId, long creatorUserId, long folderId, String[] mimeTypes,
			int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.search(
			groupId, creatorUserId, folderId, mimeTypes, status, start, end);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 #updateFileEntry(long, String, String, String, String,
	 String, DLVersionNumberIncrease, long, Map, File,
	 InputStream, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntry(
				long fileEntryId, String sourceFileName, String mimeType,
				String title, String description, String changeLog,
				boolean majorVersion, long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, majorVersion, fileEntryTypeId, ddmFormValuesMap, file,
			is, size, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry
			updateFileEntry(
				long fileEntryId, String sourceFileName, String mimeType,
				String title, String description, String changeLog,
				com.liferay.document.library.kernel.model.
					DLVersionNumberIncrease dlVersionNumberIncrease,
				long fileEntryTypeId,
				java.util.Map
					<String,
					 com.liferay.dynamic.data.mapping.kernel.DDMFormValues>
						ddmFormValuesMap,
				java.io.File file, java.io.InputStream is, long size,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.updateFileEntry(
			fileEntryId, sourceFileName, mimeType, title, description,
			changeLog, dlVersionNumberIncrease, fileEntryTypeId,
			ddmFormValuesMap, file, is, size, serviceContext);
	}

	@Override
	public com.liferay.document.library.kernel.model.DLFileEntry updateStatus(
			long userId, long fileVersionId, int status,
			com.liferay.portal.kernel.service.ServiceContext serviceContext,
			java.util.Map<String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.updateStatus(
			userId, fileVersionId, status, serviceContext, workflowContext);
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.verifyFileEntryCheckOut(
			fileEntryId, lockUuid);
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryService.verifyFileEntryLock(fileEntryId, lockUuid);
	}

	@Override
	public DLFileEntryService getWrappedService() {
		return _dlFileEntryService;
	}

	@Override
	public void setWrappedService(DLFileEntryService dlFileEntryService) {
		_dlFileEntryService = dlFileEntryService;
	}

	private DLFileEntryService _dlFileEntryService;

}