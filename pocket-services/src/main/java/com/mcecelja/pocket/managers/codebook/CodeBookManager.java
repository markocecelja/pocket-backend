package com.mcecelja.pocket.managers.codebook;

import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.AbstractCodeBookEntity;

public interface CodeBookManager {

	AbstractCodeBookEntity getCodeBookEntity(Long id) throws PocketException;
}
