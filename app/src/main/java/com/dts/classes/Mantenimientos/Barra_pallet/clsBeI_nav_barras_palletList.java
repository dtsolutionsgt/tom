package com.dts.classes.Mantenimientos.Barra_pallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeI_nav_barras_palletList {
    @ElementList(inline=true,required = false)
    public List<clsBeI_nav_barras_pallet> items = new List<clsBeI_nav_barras_pallet>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeI_nav_barras_pallet> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeI_nav_barras_pallet clsBeI_nav_barras_pallet) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeI_nav_barras_pallet> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeI_nav_barras_pallet> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeI_nav_barras_pallet get(int index) {
            return null;
        }

        @Override
        public clsBeI_nav_barras_pallet set(int index, clsBeI_nav_barras_pallet element) {
            return null;
        }

        @Override
        public void add(int index, clsBeI_nav_barras_pallet element) {

        }

        @Override
        public clsBeI_nav_barras_pallet remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeI_nav_barras_pallet> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeI_nav_barras_pallet> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeI_nav_barras_pallet> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}